package com.project.meishixing.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.meishixing.R;

public class LoginActivity extends BaseActivity {
	private ImageView mBackView;
	private TextView mLoginView;
	private TextView mRegisterView;
	private ImageView mFinishView;
	private TextView mTitleView;
	private TableRow mLoginFromSinaRow;
	private TableRow mLoginFromQQRow;
	private TextView mMidTextView;
	private TableLayout mLoginPager;
	private EditText mLoginUsernameView;
	private EditText mLoginPassWordView;
	private TableLayout mRegisterPager;
	private EditText mRegisterUsername;
	private EditText mRegisterpwd;
	private EditText mRegisterEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_main);
		init();
	}
	private void init(){
		mBackView=(ImageView) findViewById(R.id.header_left_imgview);
		mLoginView=(TextView) findViewById(R.id.header_center_text_login);
		mRegisterView=(TextView) findViewById(R.id.header_center_text_register);
		mFinishView=(ImageView) findViewById(R.id.header_right_imgview);
		mTitleView=(TextView) findViewById(R.id.login_register_other_tips);
		mLoginFromSinaRow=(TableRow) findViewById(R.id.login_by_sina);
		mLoginFromQQRow=(TableRow) findViewById(R.id.login_by_qq);
		mMidTextView=(TextView) findViewById(R.id.login_register__main_tips);
		mLoginPager=(TableLayout) findViewById(R.id.login_page);
		mLoginPassWordView=(EditText) mLoginPager.findViewById(R.id.login_password_edit);
		mLoginUsernameView=(EditText) mLoginPager.findViewById(R.id.login_username_edit);
		mRegisterPager=(TableLayout) findViewById(R.id.register_page);
		mRegisterEmail=(EditText) mRegisterPager.findViewById(R.id.register_email_edit);
		mRegisterUsername=(EditText) mRegisterPager.findViewById(R.id.register_nickname_edit);
		mRegisterpwd=(EditText) mRegisterPager.findViewById(R.id.register_password_edit);	
	}
	public void myChick(View v){
		switch (v.getId()) {
		case R.id.header_left_imgview://返回按钮
			finish();
			break;
		case R.id.header_center_text_login://选择登录按钮
			if(mRegisterPager.isShown()){
				mRegisterPager.setVisibility(View.GONE);
				mLoginPager.setVisibility(View.VISIBLE);
				mTitleView.setText("登录到美食行");
				mLoginView.setBackgroundResource(R.drawable.segment_left_on);
				mRegisterView.setBackgroundResource(R.drawable.segment_right_off);
				mMidTextView.setText("或者使用美食行账户登录");
			}
			break;
		case R.id.header_center_text_register://选择注册按钮
			if(mLoginPager.isShown()){
				mLoginPager.setVisibility(View.GONE);
				mRegisterPager.setVisibility(View.VISIBLE);
				mTitleView.setText("加入到美食行");
				mLoginView.setBackgroundResource(R.drawable.segment_left_off);
				mRegisterView.setBackgroundResource(R.drawable.segment_right_on);
				mMidTextView.setText("或者简单填写信息完成注册");
			}
			break;
		case R.id.header_right_imgview://完成按钮
			
			break;
		case R.id.login_register_other_tips://标题按钮
			
			break;
		case R.id.login_by_sina://选择从新浪登录
			
			break;
		case R.id.login_by_qq://选择从QQ登录
			
			break;
			

		default:
			break;
		}
	}
	
	
	
	
	
	
	
	
	
}
