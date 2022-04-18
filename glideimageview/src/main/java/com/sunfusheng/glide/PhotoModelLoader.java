package com.sunfusheng.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

public class PhotoModelLoader extends BaseGlideUrlLoader<GlideThumb> {

    protected PhotoModelLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    protected PhotoModelLoader(ModelLoader<GlideUrl, InputStream> concreteLoader, @Nullable ModelCache<GlideThumb, GlideUrl> modelCache) {
        super(concreteLoader, modelCache);
    }

    @Override
    protected String getUrl(GlideThumb glideThumb, int width, int height, Options options) {
        String url = "";
        if (glideThumb.getUrl().contains("?") == true) {
            url = glideThumb.getUrl() + "&x-oss-process=image/resize,m_fill,h_"+height+",w_"+width;
        } else {
            url = glideThumb.getUrl() + "?x-oss-process=image/resize,m_fill,h_"+height+",w_"+width;
        }
        Log.e("TAG", "getUrl: "+url );
        return url;
    }

    @Override
    public boolean handles(@NonNull GlideThumb glideThumb) {
        return true;
    }

    public  static class Factory implements ModelLoaderFactory<GlideThumb, InputStream> {

        private final ModelCache<GlideThumb, GlideUrl> modelCache = new ModelCache<>(500);


        @NonNull
        @Override
        public ModelLoader<GlideThumb, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new PhotoModelLoader(multiFactory.build(GlideUrl.class, InputStream.class), modelCache);
        }

        @Override
        public void teardown() {

        }
    }


}