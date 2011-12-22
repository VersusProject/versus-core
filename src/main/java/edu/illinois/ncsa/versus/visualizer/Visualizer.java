package edu.illinois.ncsa.versus.visualizer;

import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.adapter.Adapter;

import java.awt.image.BufferedImage;

public interface Visualizer {
	
	public BufferedImage getImage2dPreview(Extractor extractor, Adapter adapter);
	
	public String getName();	
}