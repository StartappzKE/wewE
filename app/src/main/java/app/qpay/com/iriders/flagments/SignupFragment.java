package app.qpay.com.iriders.flagments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import app.qpay.com.iriders.R;
import app.qpay.com.iriders.application.Config;

import static app.qpay.com.iriders.application.Config.NOT_FOUND;
import static app.qpay.com.iriders.application.Config.VERIFY_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    EditText editTextFirstName,editTextIdNumber;
    AppCompatButton btn_signup;

    public SignupFragment() {
        // Required empty public constructor
    }

    ProgressDialog progressDialog;
    Fragment currentFragment = null;
    FragmentTransaction ft;
     View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_signup, container, false);
         editTextFirstName = v.findViewById(R.id.input_name);
         editTextIdNumber = v.findViewById(R.id.input_id_no);
         btn_signup = v.findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        return  v;
    }

    private void login(){

        progressDialog = ProgressDialog.show(getActivity(),
                "I-Rider", "Verifying your data. Please wait");
        //Getting values from edit texts
        final String email = editTextFirstName.getText().toString().trim();
        final String id_no = editTextIdNumber.getText().toString().trim();

                //Creating a string request
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        VERIFY_URL+"?"+"id="+id_no,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                Log.i("Response",response);
                                Log.i("id",id_no);
                                if(response.contains(NOT_FOUND)){

                                  //Starting profile activity

                                    //If the server response is not success
                                    //Displaying an error message on toast
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),
                                            "The ID number is not linked to any Driving Licence",
                                            Toast.LENGTH_LONG).show();

                                }else{

                                    //Creating a shared preference
                                    progressDialog.dismiss();
                                    /*SharedPreferences sharedPreferences = getActivity()
                                            .getSharedPreferences(Config.SHARED_PREF_NAME,
                                                    Context.MODE_PRIVATE);

                                    //Creating editor to store values to shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    //Adding values to editor
                                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                    editor.putString(Config.EMAIL_SHARED_PREF, email);

                                    //Saving values to editor
                                    editor.commit();*/

                                    currentFragment = new PersonalDataFragment();
                                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.containerView, currentFragment);
                                    ft.commit();


                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                progressDialog.dismiss();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put(Config.KEY_ID_NO, id_no);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);

        /*stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

    }


}
