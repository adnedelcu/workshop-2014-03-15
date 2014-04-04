package com.zitec.workshopz.user.views;

import com.zitec.workshopz.R;
import com.zitec.workshopz.user.activities.LoginActivity;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterDialog extends DialogFragment {
	Button register;
	
	EditText name;
	EditText email;
	EditText phone_number;
	EditText position;
	EditText password;

	SparseArray<String> errors;

	LoginActivity act;
	
//	public RegisterDialog() {
//		initDialog();
//		this.errors = new SparseArray<String>();
//		this.errors.put(R.id.name, this.act.getResources().getString(R.string.empty_name_error));
//		this.errors.put(R.id.email, this.act.getResources().getString(R.string.empty_email_error));
//		this.errors.put(R.id.phone_number, this.act.getResources().getString(R.string.empty_phone_error));
//		this.errors.put(R.id.position, this.act.getResources().getString(R.string.empty_position_error));
//		this.errors.put(R.id.password, this.act.getResources().getString(R.string.empty_password_error));
//	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
//		this.act = (LoginActivity)this.getActivity();
		View view = inflater.inflate(R.layout.register_dialog, container);
		getDialog().setTitle("Register");
		
		register = (Button) view.findViewById(R.id.register);
		name = (EditText) view.findViewById(R.id.name);
		email = (EditText) view.findViewById(R.id.email);
		phone_number = (EditText) view.findViewById(R.id.phone_number);
		position = (EditText) view.findViewById(R.id.position);
		password = (EditText) view.findViewById(R.id.password);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		return view;
	}
}
