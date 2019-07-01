package hw2code;
import org.apache.commons.math3.linear.*;



public class matrixCalculations {

	private RealMatrix thisIfTdfMatrix;
	private RealMatrix UMatrix;
	public matrixCalculations(double[][] ifTdfMatrix) {
		this.thisIfTdfMatrix= new Array2DRowRealMatrix(ifTdfMatrix);
	}
	
	public void printThisIfTdfMatrix() {
		System.out.println(this.thisIfTdfMatrix.getRowDimension());
		System.out.println(this.thisIfTdfMatrix.getColumnDimension());
	}
	
	//get the U Matrix for row dimension
	public RealMatrix getUMatrix() {
		SingularValueDecomposition svdMatrix = 
				new SingularValueDecomposition(this.thisIfTdfMatrix);
		this.UMatrix=svdMatrix.getU();
		return this.UMatrix;
		
	}
	
	public double[][] getTwoColumnsOfU(){
		RealMatrix currUMatrix=getUMatrix();
		double[][] twoColumnsOfU = new double[this.thisIfTdfMatrix.getRowDimension()][2];
		for(int j=0; j<2;j++) {
			double[] currentColumn = currUMatrix.getColumn(j);
			for(int rowNum=0;rowNum<currentColumn.length;rowNum++) {
				twoColumnsOfU[rowNum][j]=currentColumn[rowNum];
			}
			
		}
		
		return twoColumnsOfU;
	}
	
}
