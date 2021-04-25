//package com.yongliang.houylPage.section.sectionProducts.consultSectionProduct;
//
//import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.databinding.ObservableList;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.txx.app.main.R;
//import com.txx.app.main.databinding.ItemConsultTypeConsultOneBinding;
//import com.txx.app.main.databinding.ItemConsultTypeConsultTwoBinding;
//import com.txx.app.main.databinding.ItemConsultTypeProductOneBinding;
//import com.txx.app.main.databinding.ItemConsultTypeProductTwoBinding;
//import com.txx.app.main.databinding.ItemNoneBinding;
//import com.txx.app.main.databinding.ItemTitleProductBinding;
//import com.txx.app.main.section.sectionProducts.beans.ConsultBean;
//
//public class ConsultRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    public ObservableList<ConsultBean> list;
//    public Context context;
//    public boolean isResume = true;
//    public boolean isShowImg = false;
//
//    public ConsultRecyclerviewAdapter(Context context, ObservableList<ConsultBean> list) {
//        this.list = list;
//        this.context = context;
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return list.get (position).getTypes ();
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        if (viewType == ConsultBean.CONSULT_ONE) {
//            ItemConsultTypeConsultOneBinding binding = DataBindingUtil.inflate (LayoutInflater.from (parent.getContext ()), R.layout.item_consult_type_consult_one, parent, false);
//            return new OneViewHolder (binding);
//        } else if (viewType == ConsultBean.CONSULT_TWO) {
//            ItemConsultTypeConsultTwoBinding binding = DataBindingUtil.inflate (LayoutInflater.from (parent.getContext ()), R.layout.item_consult_type_consult_two, parent, false);
//            return new TwoViewHolder (binding);
//        } else if (viewType == ConsultBean.PRODUCT_ONE) {
//            ItemConsultTypeProductOneBinding binding = DataBindingUtil.inflate (LayoutInflater.from (parent.getContext ()), R.layout.item_consult_type_product_one, parent, false);
//            return new ThreeViewHolder (binding);
//        } else if (viewType == ConsultBean.PRODUCT_TWO) {
//            ItemConsultTypeProductTwoBinding binding = DataBindingUtil.inflate (LayoutInflater.from (parent.getContext ()), R.layout.item_consult_type_product_two, parent, false);
//            return new FourViewHolder (binding);
//        } else {
//            ItemNoneBinding binding = DataBindingUtil.inflate (LayoutInflater.from (parent.getContext ()), R.layout.item_none, parent, false);
//            return new EmptyViewHolder (binding);
//        }
//    }
//
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        final ConsultBean item = list.get (holder.getAdapterPosition ());
//        if (item == null) {
//            return;
//        }
////    点击事件统一处理
//        holder.itemView.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.OnClickListener (holder.itemView, holder.getAdapterPosition (), item);
//                }
//
//            }
//        });
//        holder.itemView.setOnLongClickListener (new View.OnLongClickListener () {
//            @Override
//            public boolean onLongClick(View v) {
//                if (listener != null) {
//                    listener.OnLongClickListener (holder.itemView, holder.getAdapterPosition (), holder, item);
//                }
//                return false;
//            }
//        });
//
//        if (holder instanceof OneViewHolder) {
//
//
//        } else if (holder instanceof TwoViewHolder) {
//
////
////            if (bean.getLabels () != null && bean.getLabels ().size () > 0) {
//////                只取第一个
////                TkMainTtListBean.InfoBean.ListBean.LabelsBean labelsBean = bean.getLabels ().get (0);
////                ((NormalViewHolder) holder).getBinding ().tvHomeBriefIntroduction.setText (labelsBean.getName ());
////                ((NormalViewHolder) holder).getBinding ().tvHomeBriefIntroduction.setVisibility (View.VISIBLE);
////                ((NormalViewHolder) holder).getBinding ().view.setVisibility (View.VISIBLE);
////                try {
////                    ((NormalViewHolder) holder).getBinding ().tvHomeBriefIntroduction.setTextColor (Color.parseColor (labelsBean.getColor ()));
////                    GradientDrawable myShape = (GradientDrawable) ((NormalViewHolder) holder).getBinding ().view.getBackground ();
////                    myShape.setColor (Color.parseColor (labelsBean.getColor ()));//绿色
////                } catch (Exception e) {
////                }
////                ((NormalViewHolder) holder).getBinding ().tvHomeNum.setPadding (DensityUtil.dip2px (context, 5), 0, 0, 0);
////
////            } else {
////                ((NormalViewHolder) holder).getBinding ().tvHomeBriefIntroduction.setVisibility (View.GONE);
////                ((NormalViewHolder) holder).getBinding ().view.setVisibility (View.GONE);
////                ((NormalViewHolder) holder).getBinding ().tvHomeNum.setPadding (0, 0, 0, 0);
////            }
////            String body = TextUtils.isEmpty (bean.getTitle ()) ? "— —" : bean.getTitle ();
////            ((NormalViewHolder) holder).getBinding ().tvHomeBody.setText (body);
////            Glide.with (context)
////                    .load (bean.getFaceImgUrl ())
////                    .asBitmap ().transform (new GlideRoundTransform (context, 4))
////                    .placeholder (R.mipmap.iv_default)
////                    .error (R.mipmap.iv_default)
////                    .into (((NormalViewHolder) holder).getBinding ().imgHomeBody);
////            String num = getStrNum (bean.getReadNum ()) + "看过·" + getStrNum (bean.getTransmitNum ()) + "转发";
////            ((NormalViewHolder) holder).getBinding ().tvHomeNum.setText (num);
//        }else  if(holder instanceof ThreeViewHolder){
//
//        }else  if(holder instanceof FourViewHolder){
//
//        }
//
//    }
////
////    public void setResume(boolean isResume) {
////        this.isResume = isResume;
////
////    }
////
////    public void isShowImg(boolean isShowImg) {
////        this.isShowImg = isShowImg;
////        notifyFirst ();
////    }
////
////    public void notifyFirst() {
////        if (getItemCount () > 0) {
////            notifyItemChanged (0);
////        }
////    }
//
//    @Override
//    public int getItemCount() {
//        return list == null ? 0 : list.size ();
//    }
//
//
////    CONSULT_ONE
//    public class OneViewHolder extends RecyclerView.ViewHolder {
//        public ItemConsultTypeConsultOneBinding binding;
//
//        public OneViewHolder(ItemConsultTypeConsultOneBinding binding) {
//            super (binding.getRoot ());
//            this.binding = binding;
//        }
//
//        public ItemConsultTypeConsultOneBinding getBinding() {
//            return binding;
//        }
//    }
////    CONSULT_TWO
//    public class TwoViewHolder extends RecyclerView.ViewHolder{
//        public ItemConsultTypeConsultTwoBinding binding;
//
//        public TwoViewHolder(ItemConsultTypeConsultTwoBinding binding) {
//            super (binding.getRoot ());
//            this.binding = binding;
//        }
//
//        public ItemConsultTypeConsultTwoBinding getBinding() {
//            return binding;
//        }
//    }
//
//
//    //PRODUCT_ONE
//    public class ThreeViewHolder extends RecyclerView.ViewHolder {
//        public ItemConsultTypeProductOneBinding binding;
//
//        public ThreeViewHolder(ItemConsultTypeProductOneBinding binding) {
//            super (binding.getRoot ());
//            this.binding = binding;
//        }
//
//        public ItemConsultTypeProductOneBinding getBinding() {
//            return binding;
//        }
//    }
//
//    //PRODUCT_TWO
//    public class FourViewHolder extends RecyclerView.ViewHolder {
//        public ItemConsultTypeProductTwoBinding binding;
//
//        public FourViewHolder(ItemConsultTypeProductTwoBinding binding) {
//            super (binding.getRoot ());
//            this.binding = binding;
//        }
//
//        public ItemConsultTypeProductTwoBinding getBinding() {
//            return binding;
//        }
//    }
//
//
//    //空闲
//    public class EmptyViewHolder extends RecyclerView.ViewHolder {
//        public ItemNoneBinding binding;
//
//        public EmptyViewHolder(ItemNoneBinding binding) {
//            super (binding.getRoot ());
//            this.binding = binding;
//        }
//
//        public ItemNoneBinding getBinding() {
//            return binding;
//        }
//    }
//
//
//
//
//    private OnClickListener listener;
//
//    //点击事件处理
//    public OnClickListener getListener() {
//        return listener;
//    }
//
//    public void setListener(OnClickListener listener) {
//        this.listener = listener;
//    }
//
//    public interface OnClickListener {
//        //type 是那种类型，0-7 ，title Type 为跳转到其余四个里面的类型
//        void OnClickListener(View v, int possion, ConsultBean bean);
//
//        void OnMoreClickListener(View v, int possion, ConsultBean bean);
//
//        void OnImgClickListener(View v, int possion, ConsultBean bean);
//
//
//        boolean OnLongClickListener(View v, int possion, RecyclerView.ViewHolder vh, ConsultBean bean);
//    }
//
////    //    数字转为万
////    public String getStrNum(String str) {
////        if (!TextUtils.isEmpty (str)) {
////            int num = 0;
////            try {
////
////                num = Integer.parseInt (str);
////
////            } catch (NumberFormatException e) {
////                e.printStackTrace ();
////            }
////            if (num / 10000 > 1) {
////
////                BigDecimal a = new BigDecimal (num / 10000);
////                return a.setScale (1, BigDecimal.ROUND_DOWN) + "万";
////            }
////
////            return num + "";
////        }
////
////        return "0";
////    }
//
//}