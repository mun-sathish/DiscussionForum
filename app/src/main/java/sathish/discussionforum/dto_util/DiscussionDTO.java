package sathish.discussionforum.dto_util;

import java.io.Serializable;

/**
 * Created by sathish on 2/10/15.
 */
public class DiscussionDTO implements Serializable {

    private String  userID;
    private String  profileImage;
    private String  displayName;
    private String  profileLink;
    private String  questionTitle;
    private String  viewCount;
    private String  answerCount;
    private String  questionID;
    private String  questionLink;
    private Boolean isAnswered;



    public String getUserID() { return userID; }
    public String getProfileImage() { return profileImage; }
    public String getDisplayName() { return displayName; }
    public String getProfileLink() { return profileLink; }
    public String getQuestionTitle() { return questionTitle; }
    public String getViewCount() { return viewCount; }
    public String getAnswerCount() { return answerCount; }
    public String getQuestionID() { return questionID; }
    public String getQuestionLink() { return questionLink; }
    public Boolean getIsAnswered() { return isAnswered; }


    public void setUserID(String userID){ this.userID = userID; }
    public void setProfileImage(String profileImage){ this.profileImage = profileImage; }
    public void setDisplayName(String displayName){ this.displayName = displayName; }
    public void setProfileLink(String profileLink){ this.profileLink = profileLink; }
    public void setQuestionTitle(String questionTitle){ this.questionTitle = questionTitle; }
    public void setViewCount(String viewCount){ this.viewCount = viewCount; }
    public void setAnswerCount(String answerCount){ this.answerCount = answerCount; }
    public void setQuestionID(String questionID){ this.questionID = questionID; }
    public void setQuestionLink(String questionLink){ this.questionLink = questionLink; }
    public void setIsAnswered(Boolean isAnswered){ this.isAnswered = isAnswered; }


}
