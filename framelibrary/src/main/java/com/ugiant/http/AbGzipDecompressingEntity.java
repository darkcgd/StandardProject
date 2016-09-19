package com.ugiant.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.HttpEntity;
/**
 * © 2012 amsoft.cn
 * 名称：AbGzipDecompressingEntity.java 
 * 描述：Http解压内容
 *
 * @author cgd
 * @date 2016-01-11
 */
public class AbGzipDecompressingEntity extends HttpEntityWrapper{
    
    public AbGzipDecompressingEntity(final HttpEntity entity){
        super(entity);
    }

    public InputStream getContent() throws IOException, IllegalStateException{
        InputStream wrappedin = wrappedEntity.getContent();
        return new GZIPInputStream(wrappedin);
    }

    public long getContentLength(){
        return -1;
    }
}