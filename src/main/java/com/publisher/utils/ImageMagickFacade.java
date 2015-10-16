package com.publisher.utils;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageMagickFacade {

	private static Log log = LogFactory.getLog(ImageMagickFacade.class);
	
	private ImageMagickFacade() { }
	
	private static ImageMagickFacade instance;
	
	public static ImageMagickFacade getInstance() {
		if (instance == null) {
			synchronized (ImageMagickFacade.class) {
				if (instance == null) {
					ImageMagickFacade imageMagickFacade = new ImageMagickFacade();
					ImageMagickFacade.instance = imageMagickFacade;
				}
			}
		}
		return instance;
	}
		
    public boolean resize(String magickPath, 
    								   File source, 
    								   int sourceWidth, 
    								   int sourceHeight, 
    								   File target, 
    								   int targetWidth, 
    								   int targetHeight, 
    								   Float quality, 
    								   Float centerX, 
    								   Float centerY) {
        if (centerX == null) {
        	centerX = 0.5f;
        }
        if (centerY == null) {
        	centerY = 0.5f;
        }
        
        Integer cw = null;
        Integer ch = null;

        double r = 1d * targetWidth / targetHeight;
        double r0 = 1d * sourceWidth / sourceHeight;

        int wi = sourceWidth;
        int hi = sourceHeight;
        int sx1 = 0;
        int sy1 = 0;
        int sx2 = (int) sourceWidth;
        int sy2 = (int) sourceHeight;
        
        if (r < r0) {        	
            hi  = sourceHeight;            
            wi  = (int) Math.floor(r * hi);            
            sx1 = (int) Math.floor(centerX * sourceWidth - wi / 2);            
            if (sx1 < 0){
            	sx1 = 0;
            }            
            sx2 = sx1 + wi;            
            if (sx2 > sourceWidth) {
                sx2 = sourceWidth;
                sx1 = sx2 - wi;
            }            
        }
        if (r > r0) {
            wi  = sourceWidth;            
            hi  = (int) Math.floor(wi / r);            
            sy1 = (int) Math.floor(centerY * sourceHeight - hi / 2);            
            if (sy1 < 0) {
            	sy1 = 0;
            }            
            sy2 = sy1 + hi;            
            if (sy2 > sourceHeight) {
                sy2 = sourceHeight;
                sy1 = sy2 - hi;
            }            
        }

        cw = sx2-sx1;
        ch = sy2-sy1;
        int ox = sx1;
        int oy = sy1;

        StringBuilder command = new StringBuilder();
        command.append(magickPath + "convert ");
        command.append(source.getAbsolutePath() + " ");        
        if (cw != null) {
            command.append("-crop ");
            command.append(cw + "x" + ch + "+" + ox + "+" + oy + " ");
        }        
        command.append("-thumbnail ");
        command.append(targetWidth + "x" + targetHeight + " ");        
        if (quality != null) {        	
            quality = (float)Math.floor(quality*100);            
            if (quality < 0) {
            	quality = 75f;
            }            
            if (quality > 100) {
            	quality = 75f;
            }            
            command.append("-quality ");            
            command.append(String.valueOf(quality.intValue()) + " ");            
        }        
        command.append(target.getAbsolutePath() + " ");        
        if (log.isDebugEnabled()) {
        	log.debug(command.toString());
        }        
        return exec(command.toString());
    }

    private boolean exec(String command) {    	
        Process proc = null;
        int exitStatus = 0;
		if (log.isDebugEnabled()) {
			log.debug(command);
		}
        try {
            proc = Runtime.getRuntime().exec(command);                        
            exitStatus = proc.waitFor();
        } catch (Exception e) {
            log.error("Error when execute the command:\n" + command, e);
            exitStatus = -1;
        } finally {
        	if (proc != null) {
        		try {
        			proc.destroy();
        		} catch(Exception e) {
        			log.error(e.getMessage(), e);		
        		}    		
        	}
        }
        return (exitStatus == 0);
    }
}