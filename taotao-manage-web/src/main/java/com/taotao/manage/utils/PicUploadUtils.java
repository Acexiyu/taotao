package com.taotao.manage.utils;

import java.util.Arrays;

import javax.imageio.ImageIO;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.manage.vo.PicUploadResult;

/**
 * 上传图片工具类
 * @author Ace
 *
 */
public class PicUploadUtils {
	
	//接收的图片格式
	private static final String[] IMAGE_TYPE = {".jpg", ".jpeg", ".png", ".bmp", ".gif"};

	/**
	 * 上传图片处理
	 * @param multipartFile 图片源文件
	 * @return
	 */
	public static PicUploadResult upload(MultipartFile multipartFile) {
		//创建返回结果对象
				PicUploadResult result = new PicUploadResult();
				//设置默认失败状态
				result.setError(1);
				
				//获取文件后缀名
				String file_ext_name = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
				System.out.println(file_ext_name);
				//判断文件后缀名是否合法
				if(Arrays.asList(IMAGE_TYPE).contains(file_ext_name)) {
					
					//校验图片内容是否是图片
					try {
						//读取图片了，如果是图片则不会有异常
						ImageIO.read(multipartFile.getInputStream());
						
						//开始上传至图片服务器
						String trackerConfig  = PicUploadUtils.class.getClassLoader().getResource("tracker.conf").getPath();
						
						//设置tracker server 追踪服务器地址
						ClientGlobal.init(trackerConfig);
						
						//创建trackerClient
						TrackerClient trackerClient = new TrackerClient();
						
						//创建trackerServer
						TrackerServer trackerServer = trackerClient.getConnection();
						
						//创建storageServer
						StorageServer storageServer = null;
						
						//创建storageClient
						StorageClient storageClient = new StorageClient(trackerServer, storageServer);
						
						//上传文件
						file_ext_name = file_ext_name.replaceAll(".", "");
						String[] fileInfos = storageClient.upload_file(multipartFile.getBytes(), file_ext_name, null);
						
						//获取服务器信息
						if(fileInfos != null && fileInfos.length > 1) {
							//获取图片所在图片服务器的组名
							String groupName = fileInfos[0];
							//获取图片所在图片服务器的文件名
							String filename = fileInfos[1];
							//获取服务器信息
							ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, filename);
							//获取服务器IP地址
							String ipAddr = serverInfos[0].getIpAddr();
							
							//设置访问地址
							String url = "http://" + ipAddr + "/" + groupName + "/" + filename;
							
							System.out.println(url);
							//设置返回结果
							result.setError(0);
							result.setUrl(url);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return result;
	}
}
