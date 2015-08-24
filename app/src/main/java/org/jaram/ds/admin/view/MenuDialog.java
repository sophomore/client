package org.jaram.ds.admin.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

import org.jaram.ds.R;

/**
 * Created by KimMyoungSoo on 2015. 8. 22..
 */
public class MenuDialog extends DialogFragment {

    public MenuDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = View.inflate(getActivity(),R.layout.fragment_menu_dialog,null);
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(view);

        return dialog;
    }
}
