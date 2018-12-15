//package com.wecareit.adapter;
//
//public class MultiSpinnerAdapter extends ArrayAdapter<StateVO> {
//    private Context mContext;
//    private ArrayList<StateVO> listState;
//    private MyAdapter myAdapter;
//    private boolean isFromView = false;
//
//    public MyAdapter(Context context, int resource, List<StateVO> objects) {
//        super(context, resource, objects);
//        this.mContext = context;
//        this.listState = (ArrayList<StateVO>) objects;
//        this.myAdapter = this;
//    }
//
//    @Override
//    public View getDropDownView(int position, View convertView,
//                                ViewGroup parent) {
//        return getCustomView(position, convertView, parent);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return getCustomView(position, convertView, parent);
//    }
//
//    public View getCustomView(final int position, View convertView,
//                              ViewGroup parent) {
//
//        final ViewHolder holder;
//        if (convertView == null) {
//            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
//            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
//            holder = new ViewHolder();
//            holder.mTextView = (TextView) convertView
//                    .findViewById(R.id.text);
//            holder.mCheckBox = (CheckBox) convertView
//                    .findViewById(R.id.checkbox);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.mTextView.setText(listState.get(position).getTitle());
//
//        // To check weather checked event fire from getview() or user input
//        isFromView = true;
//        holder.mCheckBox.setChecked(listState.get(position).isSelected());
//        isFromView = false;
//
//        if ((position == 0)) {
//            holder.mCheckBox.setVisibility(View.INVISIBLE);
//        } else {
//            holder.mCheckBox.setVisibility(View.VISIBLE);
//        }
//        holder.mCheckBox.setTag(position);
//        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int getPosition = (Integer) buttonView.getTag();
//
//                if (!isFromView) {
//                    listState.get(position).setSelected(isChecked);
//                }
//            }
//        });
//        return convertView;
//    }
//
//    private class ViewHolder {
//        private TextView mTextView;
//        private CheckBox mCheckBox;
//    }
//}