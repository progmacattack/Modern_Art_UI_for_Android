package com.example.adam.moderartui_2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private DialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pull colors from layout and separate red green and blue channels for later processing

        final ColorDrawable mColor1 = (ColorDrawable) findViewById(R.id.color_block_1).getBackground();
        final int mColorInt1 = mColor1.getColor();
        final int mColor1Red = Color.red(mColorInt1);

        final ColorDrawable mColor2 = (ColorDrawable) findViewById(R.id.color_block_2).getBackground();
        final int mColorInt2 = mColor2.getColor();
        final int mColor2Red = Color.red(mColorInt2);
        final int mColor2Green = Color.green(mColorInt2);
        final int mColor2Blue = Color.blue(mColorInt2);

        final ColorDrawable mColor3 = (ColorDrawable) findViewById(R.id.color_block_3).getBackground();
        final int mColorInt3 = mColor3.getColor();
        final int mColor3Green = Color.green(mColorInt3);
        final int mColor3Blue = Color.blue(mColorInt3);

        //set up the seekbar
        SeekBar mySeekBar = (SeekBar) findViewById(R.id.slider_bar);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //let the seeker adjust a red, green, or blue value. if color requires all three initially, then use a formula

                int greenValueForColor2 = ((mColor2Green - progress) > 0 ) ? (mColor2Green - progress) : 0;
                int blueValueForColor2 = ((mColor2Blue + progress) <= 255 ) ? (mColor2Blue + progress) : 255;

                findViewById(R.id.color_block_1).setBackgroundColor(Color.argb(0xFF, mColor1Red, progress, progress));
                findViewById(R.id.color_block_2).setBackgroundColor(Color.argb(0xFF, mColor2Red, greenValueForColor2, blueValueForColor2));
                findViewById(R.id.color_block_3).setBackgroundColor(Color.argb(0xFF, progress, mColor3Green, mColor3Blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //unneeded but saving for potential future use
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //unneeded but saving for potential future use
            }
        });

    }

    //display menu. note that those with a physical menu key on their device must push that
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    //react to options button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case(R.id.more_information):
                showInformation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //show dialog
    void showInformation() {
        mDialog = MoreInfoDialogFragment.newInstance();
        mDialog.show(getFragmentManager(), "Dialog");
    }

    //create dialog
    public static class MoreInfoDialogFragment extends DialogFragment {

        public static MoreInfoDialogFragment newInstance() {return new MoreInfoDialogFragment();}

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Uri webpage = Uri.parse("http://www.moma.org/collection/works/79816");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.more_info_dialog)
                    .setPositiveButton(R.string.go, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                            startActivity(webIntent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
