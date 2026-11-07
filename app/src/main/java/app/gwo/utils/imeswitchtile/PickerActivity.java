package app.gwo.utils.imeswitchtile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/** A transparent, short-lived window that can become the focused IME client. */
public final class PickerActivity extends Activity {
    private static final long RETRY_INTERVAL_MS = 50;
    private static final long FOCUS_TIMEOUT_MS = 1500;
    private static final long LIFETIME_LIMIT_MS = 4000;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private EditText editor;
    private InputMethodManager inputMethodManager;
    private boolean started;
    private boolean pickerRequested;
    private boolean pickerTookFocus;
    private final Runnable finishIfNoPicker = this::finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        editor = new EditText(this);
        editor.setSingleLine(true);
        editor.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        editor.setTextColor(android.graphics.Color.TRANSPARENT);
        editor.setCursorVisible(false);
        setContentView(editor);
        editor.requestFocus();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // Never leave an invisible window on screen if focus or the picker fails.
        handler.postDelayed(finishIfNoPicker, LIFETIME_LIMIT_MS);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus && pickerRequested) {
            // The system picker normally takes window focus while it is visible.
            pickerTookFocus = true;
            handler.removeCallbacks(finishIfNoPicker);
        } else if (hasFocus && pickerTookFocus) {
            // Focus returns after the user selects an input method or cancels.
            finish();
        } else if (hasFocus && !started) {
            started = true;
            editor.requestFocus();
            waitForInputFocus(android.os.SystemClock.uptimeMillis() + FOCUS_TIMEOUT_MS);
        }
    }

    private void waitForInputFocus(long deadline) {
        if (isFinishing() || isDestroyed()) return;
        if (!hasWindowFocus()) {
            finish();
            return;
        }
        if (inputMethodManager == null) {
            finish();
        } else if (inputMethodManager.isActive(editor)
                || android.os.SystemClock.uptimeMillis() >= deadline) {
            pickerRequested = true;
            inputMethodManager.showInputMethodPicker();
        } else {
            handler.postDelayed(() -> waitForInputFocus(deadline), RETRY_INTERVAL_MS);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
