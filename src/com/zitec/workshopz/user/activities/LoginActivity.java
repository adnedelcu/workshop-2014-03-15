package com.zitec.workshopz.user.activities;

import java.util.ArrayList;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zitec.workshopz.R;
import com.zitec.workshopz.base.BaseActivity;
import com.zitec.workshopz.base.BaseEntity;
import com.zitec.workshopz.base.EntityResponseListener;
import com.zitec.workshopz.base.storage.Error;
import com.zitec.workshopz.base.validators.NotEmpty;
import com.zitec.workshopz.user.entities.User;
import com.zitec.workshopz.user.storage.adapters.UserDbAdapter;
import com.zitec.workshopz.user.storage.adapters.UserWSAdapter;
import com.zitec.workshopz.user.storage.mappers.UserMapper;
import com.zitec.workshopz.user.views.LoginView;
import com.zitec.workshopz.user.views.RegisterDialog;

public class LoginActivity extends BaseActivity {
	LoginView view;
	SparseArray<String> errors;
	private RegisterDialog registerDialog;
	
	@Override
	public void onCreate(Bundle args0){
		super.onCreate(args0);
		this.view = new LoginView(this);
		this.view.initView();
		this.view.setActions();
		this.errors = new SparseArray<String>();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login_screen, menu);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		DialogFragment prev = (DialogFragment) getSupportFragmentManager().findFragmentByTag("register_dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		
		this.registerDialog = new RegisterDialog();
		this.registerDialog.show(ft, "register_dialog");
		
		return true;
//		return super.onOptionsItemSelected(item);
	}
	
	public boolean validateLogin(SparseArray<String> values){
		boolean result = true;
		NotEmpty validator = new NotEmpty();
		int nr = values.size();
		this.errors.clear();
		for(int i = 0; i < nr; i++){
			int key = values.keyAt(i);
			if(!validator.validate(values.valueAt(i))){
				result = false;
				this.errors.put(key, this.view.getEmptyError(key));
			}
		}
		return result;
	}
	
	public SparseArray<String> getErrors(){
		return this.errors;
	}
	
	public void login(String username, String password){
		final UserMapper mapper = new UserMapper();
		mapper.setAdapter(new UserWSAdapter(this));
		mapper.setListener(new EntityResponseListener() {
			
			@Override
			public void onSuccess(ArrayList<BaseEntity> obj) {
				if(obj.size() < 1){
					LoginActivity.this.showGenericError(
							LoginActivity.this,
							new Error(LoginActivity.this.getResources().getString(R.string.network_error)));
					return;
				}
				User usr = (User) obj.get(0);
				BaseActivity.identity = usr;
				try {
					mapper.setAdapter(new UserDbAdapter(LoginActivity.this));
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				usr.setCurrentIdentity("true");
				mapper.save(usr);
				LoginActivity.this.loadWorkshops();
			}
			
			@Override
			public void onError(Error err) {
				LoginActivity.this.showGenericError(
						LoginActivity.this,
						err);
			}
		});
		mapper.getEntity(username, password);
	}
}
