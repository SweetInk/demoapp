package com.huashukang.picgrab.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huashukang.picgrab.R;
import com.huashukang.picgrab.adapter.MyAdapter;
import com.huashukang.picgrab.pojo.ImageFloder;
import com.huashukang.picgrab.utils.HttpUtils;
import com.huashukang.picgrab.widget.ListImageDirPopupWindow;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class ChoosePhoto extends AppCompatActivity implements ListImageDirPopupWindow.OnImageDirSelected,HttpUtils.CallBack
{
	private ProgressDialog mProgressDialog;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 存储文件夹中的图片数量
	 */
	private int mPicsSize;
	/**
	 * 图片数量最多的文件夹
	 */
	private File mImgDir;
	/**
	 * 所有的图片
	 */
	private List<String> mImgs;

	private GridView mGirdView;
	private MyAdapter mAdapter;
	/**
	 * 临时的辅助类，用于防止同一个文件夹的多次扫描
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();

	/**
	 * 扫描拿到所有的图片文件夹
	 */

	private HttpUtils httpUtils;
	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

	private RelativeLayout mBottomLy;

	private TextView mChooseDir;
	private TextView mImageCount;
	int totalCount = 0;

	private int mScreenHeight;

	private ListImageDirPopupWindow mListImageDirPopupWindow;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			mProgressDialog.dismiss();
			// 为View绑定数据
			data2View();
			// 初始化展示文件夹的popupWindw
			initListDirPopupWindw();
		}
	};

	/**
	 * 为View绑定数据
	 */
	private void data2View()
	{
		if (mImgDir == null)
		{
			Toast.makeText(getApplicationContext(), "擦，一张图片没扫描到",
					Toast.LENGTH_SHORT).show();
			return;
		}

		mImgs = Arrays.asList(mImgDir.list());
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new MyAdapter(getApplicationContext(), mImgs,
				R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		mImageCount.setText(totalCount + "张");
	};

	/**
	 * 初始化展示文件夹的popupWindw
	 */
	private void initListDirPopupWindw()
	{
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
				mImageFloders, LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.list_dir, null));

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosepic);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		httpUtils = new HttpUtils();
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("选择图片上传");
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()){
					case R.id.item_upload:
						if(mAdapter.mSelectedImage.size()<=0){
							Toast.makeText(ChoosePhoto.this,"没有选择图片",Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(ChoosePhoto.this,"上传中...",Toast.LENGTH_SHORT).show();
							for(final String filePath:mAdapter.mSelectedImage){
								new Thread(new Runnable() {
									@Override
									public void run() {
										httpUtils.upload(new File(filePath),ChoosePhoto.this);
									}
								}).start();
							}
						}



						break;

				}
				return true;
			}
		});
		initView();
		getImages();
		initEvent();

	}

	/**
	 * 扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */

	public void find(File filess) {
		if (filess.exists()) {
			File[] files = filess.listFiles();
			for (File file : files) {

				if (file.isDirectory()) {
					File parentFile = file;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;

					//Log.i(" ")
					String[] picList = parentFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String filename) {

							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")
									|| filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					});
					int picSize = picList.length;
					if (picSize > 0) {
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);

						imageFloder.setFirstImagePath(picList[0]);
						Log.i("File Name:", picList[0]);
						imageFloder.setCount(picSize);
						mImageFloders.add(imageFloder);
					}
					totalCount += picSize;



				if (picSize > mPicsSize) {
					mPicsSize = picSize;
					mImgDir = parentFile;
				}
					find(file);
				} else {
					//Log.i("File-Name:",file.getName());

				}
			}
		}else {

		}
	}

	private void getImages()
	{
	//	Toast.makeText(ChoosePhoto.this,Environment.getExternalStorageDirectory().getAbsolutePath()+"",Toast.LENGTH_SHORT).show();
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");



		new Thread(new Runnable()
		{
			@Override
			public void run()
			{

				find(new File(Environment.getExternalStorageDirectory()+File.separator+"hskpicCache"));

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);

			}
		}).start();

	}

	/**
	 * 初始化View
	 */
	private void initView()
	{
		mGirdView = (GridView) findViewById(R.id.id_gridView);
		mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
		mImageCount = (TextView) findViewById(R.id.id_total_count);

		mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);

	}

	private void initEvent()
	{
		/**
		 * 为底部的布局设置点击事件，弹出popupWindow
		 */
		mBottomLy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				/*mListImageDirPopupWindow
						.setAnimationStyle(R.style.anim_popup_dir);*/
				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});
	}

	@Override
	public void selected(ImageFloder floder)
	{

		mImgDir = new File(floder.getDir());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String filename)
			{
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new MyAdapter(getApplicationContext(), mImgs,
				R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		// mAdapter.notifyDataSetChanged();
		mImageCount.setText(floder.getCount() + "张");
		mChooseDir.setText(floder.getName());
		mListImageDirPopupWindow.dismiss();

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_upload_pic, menu);
		return true;
	}

	@Override
	public void OnSuccess() {
		Toast.makeText(this,"上传成功",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void OnFailed(String string, Exception e) {
		Toast.makeText(this,"Failed:"+e.getMessage(),Toast.LENGTH_SHORT).show();
	}
}
