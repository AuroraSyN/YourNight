package apackage.com.yournight.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import apackage.com.yournight.R;
import apackage.com.yournight.model.access.AccessLogin;

public class FragmentProfile extends Fragment {


    private AccessLogin access;

    private static final int SELECTED_PICTURE = 1;
    ImageView imageView;
    Button imageButton;

   // TextView userID;
  //  TextView userName;
   // TextView userEmail;
   // ProfilePictureView profilePictureView;
    
    public static FragmentProfile newInstance() {
        FragmentProfile fragment = new FragmentProfile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccessLogin.initialize();
        access = AccessLogin.getInstance();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragmentprofil, container, false);

        Toolbar profilToolbar = view.findViewById(R.id.toolbarProfil);
        profilToolbar.setTitle("Profil");

        imageView =  view.findViewById(R.id.profilImage);
        imageButton = view.findViewById(R.id.btnProfil);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            SELECTED_PICTURE);
                }
                else {
                    btnClick();
                }
            }
        });

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.com_facebook_profile_picture_blank_square);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);

        //profilePictureView = (ProfilePictureView) view.findViewById(R.id.friendProfilePicture);
       // userID = view.findViewById(R.id.userID);
       // userName = view.findViewById(R.id.userName);
       // userEmail = view.findViewById(R.id.userEmail);


      //  userID.setText(access.getUserID());
       // userName.setText(access.getUserName());
       // userEmail.setText(access.getUserEmail());
        //profilePictureView.setProfileId(access.getUserID());

        return view;
    }

    public void btnClick(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PICTURE);

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK){
            if(requestCode == SELECTED_PICTURE && data != null && data.getData() != null){
                Uri uri = data.getData();
                String [] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                //InputStream inputStream;

                try {
                    // inputStream = getContentResolver().openInputStream(uri);

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    bitmap = getResizedBitmap(bitmap, 1000);

                    // Log.d(TAG, String.valueOf(bitmap));

                    //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profil_image);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                    roundedBitmapDrawable.setCircular(true);


                   ImageView imageView = getView().findViewById(R.id.profilImage);

                    imageView.setImageBitmap(bitmap);
                    imageView.setImageDrawable(roundedBitmapDrawable);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

}
