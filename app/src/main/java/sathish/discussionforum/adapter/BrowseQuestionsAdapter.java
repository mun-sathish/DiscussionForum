package sathish.discussionforum.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import sathish.discussionforum.AppController;
import sathish.discussionforum.R;
import sathish.discussionforum.dto_util.DiscussionDTO;
import sathish.discussionforum.supportingfiles.FadeInNetworkImageView;

/**
 * Created by sathish on 2/10/15.
 */
public class BrowseQuestionsAdapter extends UltimateViewAdapter {

    Activity activity;
    DiscussionDTO eachDetail;
    private List<DiscussionDTO> allRowDetail;

    // initialize the adapter
    public BrowseQuestionsAdapter(Activity activity, List<DiscussionDTO> allRowDetail, onSendDataListener sendDataListener) {
        this.activity = activity;
        this.allRowDetail = allRowDetail;
        this.sendDataListener = sendDataListener;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_browse_alumni, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getAdapterItemCount() {
        return allRowDetail.size();
    }

    @Override
    public long generateHeaderId(int position) {
        if (position == 0) {
            return position;
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if ((customHeaderView != null ? position <= getAdapterItemCount() : position < getAdapterItemCount())
                && (customHeaderView == null || position > 0)) {
            try {

                ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                eachDetail = allRowDetail.get(position);

                ((ViewHolder) holder).profileImage.setImageUrl(eachDetail.getProfileImage(), imageLoader);


                ((ViewHolder) holder).profileName.setText(eachDetail.getDisplayName());
                ((ViewHolder) holder).profileID.setText("ID: " + eachDetail.getUserID());
                ((ViewHolder) holder).questionID.setText("Question ID:  " + eachDetail.getQuestionID());
                ((ViewHolder) holder).questionTitle.setText(eachDetail.getQuestionTitle());
                ((ViewHolder) holder).viewCount.setText("View Count: " + eachDetail.getViewCount());
                ((ViewHolder) holder).isAnswered.setText("Is Answered: " + eachDetail.getIsAnswered().toString());
                ((ViewHolder) holder).answerCount.setText("Answer Count: " + eachDetail.getAnswerCount());
//                ((ViewHolder) holder)..setText(eachDetail.());
//                ((ViewHolder) holder)..setText(eachDetail.());
//                ((ViewHolder) holder)..setText(eachDetail.());
//                ((ViewHolder) holder)..setText(eachDetail.());




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_browse_alumni, parent, false);
        return new RecyclerView.ViewHolder(v) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {


        TextView profileName, profileID, questionID, questionTitle, viewCount, isAnswered, answerCount;
        FadeInNetworkImageView profileImage;
        LinearLayout onClick;

        //intialize the custom layout

        public ViewHolder(View itemView) {
            super(itemView);
            profileName = (TextView) itemView.findViewById(R.id.profileName);
            profileID = (TextView) itemView.findViewById(R.id.profileID);
            questionID = (TextView) itemView.findViewById(R.id.questionID);
            questionTitle = (TextView) itemView.findViewById(R.id.questionTitle);
            viewCount = (TextView) itemView.findViewById(R.id.viewCount);
            isAnswered = (TextView) itemView.findViewById(R.id.isAnswered);
            answerCount = (TextView) itemView.findViewById(R.id.answerCount);
            profileImage = (FadeInNetworkImageView) itemView.findViewById(R.id.profileImage);
            onClick = (LinearLayout) itemView.findViewById(R.id.cardLayout);

            onClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendDataListener.contact_info(
                        allRowDetail.get(getLayoutPosition()).getProfileLink(),
                        allRowDetail.get(getLayoutPosition()).getQuestionLink());
                   // sent what u need to send
                }
            });

        }


    }

    onSendDataListener sendDataListener;
    //set up interface
    public interface onSendDataListener {
        public void contact_info(String profileLink, String questionLink);
    }
}
