package activity.songCi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import Bean.SongCi;
import Bean.SongCiDao;
import activity.songCi.contract.SongCiContract;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.Dao;

public class SongCiModel implements SongCiContract.Model {

    private SongCiContract.Presenter mPresenter;

    public SongCiModel(SongCiContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private final String URL = "http://api.jisuapi.com/songci/search?appkey=1df53fee682ab4c8&keyword=";

    /**
     * 开始请求
     */
    @Override
    public void requestSongCi(String appId) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL + appId)
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject object = new JSONObject(response.body().string());

            if (object.getInt("status") == 0) {

                JSONObject object1 = object.getJSONObject("result");
                JSONArray jsonArray = object1.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SongCi songCi = new SongCi();
                    songCi.setTitle(jsonObject.getString("title"));
                    songCi.setType(jsonObject.getString("type"));
                    songCi.setContent(jsonObject.getString("content"));
                    songCi.setAuthor(jsonObject.getString("author"));
                    SongCi songCi1 = Dao.getInstance().getDaoSession().getSongCiDao().queryBuilder().where(SongCiDao.Properties.Title.eq(songCi.getTitle())).unique();
                    if (songCi1 == null) {
                        Dao.getInstance().getDaoSession().insert(songCi);
                    }
                    mPresenter.success(songCi);
                }
            } else {
                mPresenter.fail(object.getString("msg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}