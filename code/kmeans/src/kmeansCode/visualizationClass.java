package hw2code;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;
import org.jfree.chart.renderer.xy.*;

public class visualizationClass extends JFrame{
	
	private static final long serialVersionUID = 6294689542092367723L;
	

	
	
	//creating the visualization class
	public visualizationClass(String title, String[] NERFolders,
			ArrayList<Integer> folderBelongings, double[][] dimReductMat, Color[] colorsToUse) {
		
		super(title);
		XYSeriesCollection currentData = createSeriesCollection(NERFolders,folderBelongings,dimReductMat);
		JFreeChart newChart = ChartFactory.createScatterPlot(
				title, "x", "y", currentData);
		XYPlot newPlot= (XYPlot) newChart.getPlot();
		for(int i=0; i<NERFolders.length;i++) {
			XYItemRenderer newRender= new XYShapeRenderer();
			newPlot.setRenderer(i,newRender);
			newPlot.getRendererForDataset(newPlot.getDataset(0)).setSeriesPaint(i, colorsToUse[i]);
		}

	    ChartPanel newPanel = new ChartPanel(newChart);
	    setContentPane(newPanel);
		
	}
	//creates the collection for the series.
	public static XYSeriesCollection  createSeriesCollection(String[] NERFolders,
			ArrayList<Integer> folderBelongings, double[][] dimReductMat) {
		XYSeriesCollection  clusteredSeriesCollection = new XYSeriesCollection();
		//creating the clustered series collections strings
		for(int i=0;i<NERFolders.length;i++) {
			//XYSeries clusteredSeries=new XYSeries(String.format("Folder %d", i));
			XYSeries clusteredSeries=new XYSeries(NERFolders[i]);
			for(int docNum=0;docNum<folderBelongings.size(); docNum++) {
				if(folderBelongings.get(docNum) == i) {
					clusteredSeries.add(dimReductMat[docNum][0],dimReductMat[docNum][1]);
				}
				
			}
			clusteredSeriesCollection.addSeries(clusteredSeries);
		}
		return clusteredSeriesCollection;
	}
	
	//public static void main(String[] args) {
		
	//}
	
}
