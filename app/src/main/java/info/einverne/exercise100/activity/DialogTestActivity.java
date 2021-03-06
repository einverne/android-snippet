package info.einverne.exercise100.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.einverne.exercise100.R;

public class DialogTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);

        Button btnNormal = (Button) findViewById(R.id.btnNormalDialog);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    private void showNormalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setMessage("your message here.");
        builder.setCancelable(true);

        /**
         * Typically, a dialog is dismissed when its job is finished
         * and it is being removed from the screen.
         * A dialog is canceled when the user wants to escape the dialog
         * and presses the Back button.
         * For example, you have a standard Yes/No dialog on the screen.
         * If the user clicks No, then the dialog is dismissed
         * and the value for No is returned to the caller.
         * If instead of choosing Yes or No, the user clicks Back to escape the dialog
         * rather than make a choice then the dialog is canceled
         * and no value is returned to the caller.
         */
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(DialogTestActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(DialogTestActivity.this, "No", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showThreeOptionsDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star)
                .setTitle("喜好调查")
                .setMessage("你喜欢李连杰的电影吗？")
                .setPositiveButton("很喜欢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogTestActivity.this, "我很喜欢他的电影。",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("不喜欢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogTestActivity.this, "我不喜欢他的电影。", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("一般", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogTestActivity.this, "谈不上喜欢不喜欢。", Toast.LENGTH_LONG).show();
                    }
                }).create();
        dialog.show();
    }

    private void showInputDialog() {
        final EditText editText = new EditText(this);
        Dialog dialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Input your message")
                .setView(editText)
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputString = editText.getText().toString();
                        Toast.makeText(DialogTestActivity.this, inputString, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void showSingleChoiceDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("Single Choice")
                .setSingleChoiceItems(new String[]{"item1", "item2"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogTestActivity.this, "" + which, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void showMultiChoiceDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("Multi Choice")
                .setMultiChoiceItems(new String[]{"item0", "item1"}, new boolean[]{false, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        Toast.makeText(DialogTestActivity.this, "" + which + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                }).create();
        dialog.show();
    }

    private void showCustomDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_custom, null);
        final EditText editText = (EditText) layout.findViewById(R.id.editText);
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("Custom")
                .setView(layout)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogTestActivity.this, editText.getText().toString() + " ", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        dialog.show();
    }
}
