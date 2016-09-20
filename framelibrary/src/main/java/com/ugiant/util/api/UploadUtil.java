package com.ugiant.util.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ugiant.R;
import com.ugiant.util.AbPictureUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * 上传图片功能模块原型
 * @author cgd
 * 2016-2-23
 */
public class UploadUtil {
	private Dialog dialog;
	private  int flag;//0代表图库,1代表照相机
	private File cameraFile;
	private File path2;
	private Bitmap bitmap;
	private  final String IMGURL = Environment.getExternalStorageDirectory() + "/ugiant/changephoto/";
	/* 请求码 */
	private  final int IMAGE_REQUEST_CODE = 22;
	private  final int CAMERA_REQUEST_CODE = 33;
	private  final int RESULT_REQUEST_CODE = 44;
	private  final int SELECT_PIC_KITKAT = 55;
	
	/**
	 * 打开对话框
	 * @param activity 当前界面
	 */
	public void upload(Activity activity){
		View view = View.inflate(activity, R.layout.dialog_photo_choose_yn, null);

		Button openCamera = (Button) view.findViewById(R.id.openCamera);
		Button openPhones = (Button) view.findViewById(R.id.openPhones);
		Button cancel = (Button) view.findViewById(R.id.cancel);

		openCamera.setOnClickListener(on_click(activity,1));
		openPhones.setOnClickListener(on_click(activity,2));
		cancel.setOnClickListener(on_click(activity,3));

		dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		wl.x = 0;
		wl.y = ViewGroup.LayoutParams.MATCH_PARENT;
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	private OnClickListener on_click(final Activity activity, final int tag) {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (tag)
				{
				case 1:
					flag=1;
					openCamera(activity);
					dialog.cancel();
					break;
				case 2:
					flag=0;
					openPhones(activity);
					dialog.cancel();
					break;
				case 3:
					dialog.cancel();
					break;
				default:
					break;
				}
			}
		};
	}
	
	
	// 打开照相机
		private  void openCamera(Activity activity)
		{
			// 打开相机
			Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 判断存储卡是否可以用,存储缓存图片
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				//intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(IMGURL + IMAGE_FILE_NAME_TEMP)));
				path2 = new File(IMGURL);
				if(!path2.exists()){
					path2.mkdirs();
				}

				cameraFile = new File(path2, new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))  + ".jpg");

				intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
			}
			activity.startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
		}
		
		// 打开相册
		private  void openPhones(Activity activity)
		{
			/*Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intentFromGallery.setType("image/*"); // 设置文件类型
					intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);*/
			path2 = new File(IMGURL);
			if(!path2.exists()){
				path2.mkdirs();
			}

			Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");  
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
				activity.startActivityForResult(intent,SELECT_PIC_KITKAT);  
			} else {
				activity.startActivityForResult(intent,IMAGE_REQUEST_CODE);  
			}

		}
		
		private String imgBuffer;
		private File file;
		private String picPath = null;
		private Uri imageDataUri = null;
		/**
		 * 在onActivityResult方法中执行该方法,用于返回File
		 * @param activity 当前打开相册或打开相机的界面
		 * @param requestCode 请求码(onActivityResult方法中传过来)
		 * @param resultCode  响应码(onActivityResult方法中传过来)
		 * @param data    响应数据(onActivityResult方法中传过来)
		 * @return
		 */
		public File imageResult(Activity activity, int requestCode, int resultCode, Intent data) {
			File fileResult=null;
			int width=0;
			int height=0;
			// 结果码不等于取消时候
			if (resultCode != Activity.RESULT_CANCELED)
			{
				switch (requestCode)
				{
				case IMAGE_REQUEST_CODE:// 打开相册返回
					imageDataUri=data.getData();
					if(imageDataUri!=null){
						picPath= AbPictureUtil.getPath(activity,imageDataUri);
						//压缩大小
						getimage(picPath);
						if(picPath!=null){
							fileResult=new File(picPath);
						}else{
							fileResult=null;
						}
					}else{
						fileResult=null;
					}
					break;
				case SELECT_PIC_KITKAT:
					imageDataUri=data.getData();
					if(imageDataUri!=null){
						picPath= AbPictureUtil.getPath(activity,imageDataUri);
						//压缩大小
						getimage(picPath);
						if(picPath!=null){
							fileResult=new File(picPath);
						}else{
							fileResult=null;
						}
					}else{
						fileResult=null;
					}
					break;
				case CAMERA_REQUEST_CODE:// 打开相机返回
					String state = Environment.getExternalStorageState();
					if (state.equals(Environment.MEDIA_MOUNTED)) {
						File tempFile = new File(cameraFile.getPath());
						//压缩大小
						getimage(cameraFile.getPath());
						if(picPath!=null){
							fileResult=new File(picPath);
						}else{
							fileResult=null;
						}
					} else {
						fileResult=null;
						Toast.makeText(activity, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
					}
					break;
				}
			}
			return fileResult;
		}



	/**
	 * 图片压缩方法实现
	 * @param srcPath
	 * @return
	 */
	private Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		int hh = 800;//这里设置高度为800f
		int ww = 480;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据高度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		//System.out.println(srcPath);

		return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
	}
	/**
	 * 质量压缩
	 * @param image
	 * @return
	 */
	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		/*Drawable drawable1 = new BitmapDrawable(bitmap);
		faceImage.setImageDrawable(drawable1);
		btcontruler = true;*/
		try {
			FileOutputStream out = new FileOutputStream(IMGURL+  "camera.jpg");
			picPath=IMGURL+"camera.jpg";
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		}
		catch (Exception e) {
			e.printStackTrace();
			picPath=null;
		}
		return bitmap;
	}



	private String comp(Bitmap image, String fileName) {
		       
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
		        baos.reset();//重置baos即清空baos  
		        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
		    }  
		    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		    BitmapFactory.Options newOpts = new BitmapFactory.Options();
		    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
		    newOpts.inJustDecodeBounds = true;  
		    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		    newOpts.inJustDecodeBounds = false;  
		    int w = newOpts.outWidth;  
		    int h = newOpts.outHeight;  
		    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
		    float hh = 800f;//这里设置高度为800f  
		    float ww = 480f;//这里设置宽度为480f  
		    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
		    int be = 1;//be=1表示不缩放  
		    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
		        be = (int) (newOpts.outWidth / ww);  
		    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
		        be = (int) (newOpts.outHeight / hh);  
		    }  
		    if (be <= 0)  
		        be = 1;  
		    newOpts.inSampleSize = be;//设置缩放比例  
		    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
		    isBm = new ByteArrayInputStream(baos.toByteArray());
		    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		    //return compressImage(bitmap,fileName);//这里再进行质量压缩的意义不大，反而耗资源，删除  
		    return saveFile(bitmap, fileName);//压缩好比例大小后再进行质量压缩  
		}  
		
		  /** 
	     * 保存文件 
	     * @param bm 
	     * @param fileName 
	     * @throws IOException
	     */  
	    public String saveFile(Bitmap bm, String fileName){
	     String path = AbPictureUtil.getAlbumDir() + "/small/";
	        File dirFile = new File(path);
	        if(!dirFile.exists()){  
	            dirFile.mkdir();  
	        }  
	        File myCaptureFile = new File(path + fileName);
	        BufferedOutputStream bos=null;
	        try {
	        	bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
	        	  bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			} catch (IOException e) {
				e.printStackTrace();
			} finally{ 
				try {
					bos.flush();
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
	        return path+fileName;
	    }
		
		
		/**
		 * 裁剪图片方法实现
		 * 
		 * @param uri
		 */
		public  void startPhotoZoom(Activity activity, Uri uri)//file:///storage/sdcard1/MIUI/Gallery/cloud/.thumbnailFile/c9c997c46a62d30fc5ce3661e8b4b294cb4ad729.jpg
		{                                  //content://media/external/images/media/50
			Intent intent = new Intent("com.android.camera.action.CROP");

			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {  
				String url= AbPictureUtil.getPath(activity,uri);
				intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
			}else{  
				String url= AbPictureUtil.getPath(activity,uri);
				intent.setDataAndType(uri, "image/*");  
			} 


			//intent.setDataAndType(uri, "image/*");
			// 设置裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("return-data", true);
			activity.startActivityForResult(intent, RESULT_REQUEST_CODE);
		}
		
		private void alert(Activity activity)
		{
			Dialog dialog = new AlertDialog.Builder(activity)
			.setTitle("提示")
			.setMessage("您选择的不是有效的图片")
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					picPath = null;
				}
			})
			.create();
			dialog.show();
		}
		
}
