package org.derek.butter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

public final class ViewFinder {

    public static <T extends View> T findViewById(Activity act, int id) {
        return (T) act.findViewById(id);
    }

    public static <T extends View> T findViewById(View rootView, int id) {
        return (T) rootView.findViewById(id);
    }

    public static <T extends View> T findViewById(Fragment fragment, int id) {
        return findViewById(fragment.getView(), id);
    }
}
