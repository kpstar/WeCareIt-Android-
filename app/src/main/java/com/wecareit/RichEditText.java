package com.wecareit;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ru.noties.markwon.Markwon;

public class RichEditText extends RelativeLayout {

    private boolean isBold;
    private boolean isItalic;
    private boolean isBullet;

    public RichEditTextListener listener;
    private View rootView;
    private ImageView ivBold;
    private ImageView ivItalic;
    private ImageView ivBullets;
    private EditText edText;
    public String text;
    public void initialize(Context context) {
        isBold = false;
        isItalic = false;
        isBullet = false;

        rootView = (RelativeLayout)inflate(context, R.layout.richtext_editor, this);

        ivBold = rootView.findViewById(R.id.richBold);
        ivItalic = rootView.findViewById(R.id.richItalic);
        ivBullets = rootView.findViewById(R.id.richBullets);
        edText = rootView.findViewById(R.id.etDesc);

        final SpanWatcher watcher = new SpanWatcher() {
            @Override
            public void onSpanAdded(final Spannable text, final Object what,
                                    final int start, final int end) {
                // Nothing here.
            }

            @Override
            public void onSpanRemoved(final Spannable text, final Object what,
                                      final int start, final int end) {
                // Nothing here.
            }

            @Override
            public void onSpanChanged(final Spannable text, final Object what,
                                      final int ostart, final int oend, final int nstart, final int nend) {
                Toast.makeText(context, "Changed", Toast.LENGTH_LONG).show();
                if (what == Selection.SELECTION_START) {
                    // Selection start changed from ostart to nstart.
                } else if (what == Selection.SELECTION_END) {
                    // Selection end changed from ostart to nstart.
                }
            }
        };

        edText.getText().setSpan(watcher, 0, 0, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("Change Value = ", text);
                text = charSequence.toString();
                edText.setSelection(i);
                listener.onChanged(charSequence, i, i1, i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ivBold.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                isBold = !isBold;
                customizeFont();
                int mId = R.drawable.ic_bold_checked;
                if (isBold == false) mId = R.drawable.ic_bold;
                ivBold.setImageDrawable(context.getDrawable(mId));
            }
        });

        ivItalic.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                isItalic = !isItalic;
                customizeFont();
                int mId = R.drawable.ic_italic_checked;
                if (isItalic == false) mId = R.drawable.ic_italic;
                ivItalic.setImageDrawable(context.getDrawable(mId));
            }
        });

        ivBullets.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                isBullet = !isBullet;
                int mId = R.drawable.ic_bullets_checked;
                if (isBullet == false) mId = R.drawable.ic_bullets;
                ivBullets.setImageDrawable(context.getDrawable(mId));
            }
        });

    }

    public void customizeFont() {
        if (isBold && isItalic) {
            edText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            return;
        } else if (isBold) {
            edText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            return;
        } else if (isItalic) {
            edText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            return;
        }
        edText.setTypeface(null);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public RichEditText(Context context) {
        super(context);
        initialize(context);
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }

    public void setBullet(boolean bullet) {
        isBullet = bullet;
    }

    public interface RichEditTextListener {
        public void onChanged(CharSequence s, int start, int before, int count);
    }

}
