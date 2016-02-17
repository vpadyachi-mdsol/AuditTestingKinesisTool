import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

public class AuditTable extends JTable {
	int colSelect;
	boolean select;

	//default constructor
	public AuditTable(){
           super();
           showHorScroll(true);
           }


	//constructor to create a table with given number of ros and columns
        public AuditTable(int row, int col){
            super(row, col);
            showHorScroll(true);
            }


	 /**this method shows the horizontal scroll bar when required.
	   *make sure it is called for other methods like setHeaderWidth etc to work properly
       * Its being called in the two constructors provided here
       */
        public void showHorScroll(boolean show){
          if (show){
                 setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                 }
            else{
                 setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			 }
        }


     /**this method should be called to set the column
	   *at pColumn index to a width of pWidth
	   */
		public void setColumnWidth(int pColumn, int pWidth){
		//get the column model
		TableColumnModel colModel = getColumnModel();
		//get the column at index pColumn, and set its preferred width
		colModel.getColumn(pColumn).setPreferredWidth(pWidth);
		}

	 /**this method would set pColumn resizable or not based on the flag: pResizable
		*/
		public void setResizable(int pColumn, boolean pIsResize){
		//get the column model
		TableColumnModel colModel = getColumnModel();
		//set resizable or not
		colModel.getColumn(pColumn).setResizable(pIsResize);
		}


	 /**sets the column at index col to wither be selected or deselected -based on the
      * value of select
     */
        public void setSelect(int col, boolean select){
	            colSelect = col;
	            this.select = select;
	     }


	  /**This method returns whether a particular cell is selected or not
	  */
	  public boolean isCellSelected(int row, int column) throws IllegalArgumentException{
	     //over ride the method for the column set in setSelect()
	     if (colSelect == column){
	        if (select) return true;
	       	  else return false;
	        }
	        else{
	           return super.isCellSelected(row, column);
	            }
	        }


		/**sets the header and column size as per the Header text
		*/
		public void setHeaderSize(int pColumn){
			//get the column name of the given column
			String value =  getColumnName(pColumn);
			//calculate the width required for the column
			FontMetrics metrics = getGraphics().getFontMetrics();
			int width = metrics.stringWidth(value) + (2*getColumnModel().getColumnMargin());
			//set the width
			setColumnWidth(pColumn, width);
		}

    }// end of MyTable class