package zhangli.newsapp.news;

import java.util.List;

/**
 * Created by scxh on 2016/1/11.
 */
public class TAll {
    private int replyCount;
    private String digest;
    private String skipType;
    private String title;
    private String imgsrc;
    private List<imgextra> imgextra;
    private List<ads> ads;

    public List<ads> getAds() {
        return ads;
    }

    public void setAds(List<ads> ads) {
        this.ads = ads;
    }

    public List<imgextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<imgextra> imgextra) {
        this.imgextra = imgextra;
    }

    public String getImgsrc () {
        return imgsrc ;
    }

    public void setImgsrc(String imgsrc ) {
        this.imgsrc  = imgsrc ;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
