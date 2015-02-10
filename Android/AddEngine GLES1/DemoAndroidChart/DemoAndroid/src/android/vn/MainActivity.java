package android.vn;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
 
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
 
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends Activity {
	private GraphicalView mChart;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Khởi tạo biểu đồ
        createChart();
    }
 
    private void createChart(){
    	//Khởi tạo biểu đồ gồm 5 giá trị thời gian
        int count = 5;
        Date[] dt = new Date[5];
        for(int i=0;i<count;i++){
            GregorianCalendar gc = new GregorianCalendar(2012, 10, i+1);
            dt[i] = gc.getTime();
            
            
            
        }
        
        String[] product = {"JX1", "JX2", "JXF", "FS", "WJX", "ZS"};
 
        //Mảng giá trị đầu vào
/*        int[] visits = {2000,2500,2700,2100,2800};
        int[] Likes = {2200, 2700, 2900, 2800, 3200};
*/        
        
        int[] review = {3, 1, 2, 0, 3, 0};
        int[] dev = {8, 2, 0, 6, 2, 2};

 
        // Khởi tạo TimeSeries là lượt Visits
        TimeSeries visitsSeries = new TimeSeries("Visits");
 
        // Khởi tạo TimeSeries là lượt Likes
        TimeSeries LikesSeries = new TimeSeries("Likes");
 
        // Thêm dữ liệu đồng loạt vào lượt Visits and lượt Likes 
     /*   for(int i=0;i<dt.length;i++){
            visitsSeries.add(dt[i], review[i]);
            LikesSeries.add(dt[i],dev[i]);
        }*/
        
                
        for(int i=0;i<product.length;i++){
            visitsSeries.add(product[i], review[i]);
            LikesSeries.add(product[i],dev[i]);
            
        }
 
        //Khởi tạo 1 dataset để quản lý tất cả các giá trị
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
 
        //Thêm tất cả thông tin lượt Visits vào dataset
        dataset.addSeries(visitsSeries);
 
        //Thêm tất cả thông tin lượt Likes vào dataset
        dataset.addSeries(LikesSeries);
 
        //Tạo XYSeriesRenderer để tùy chỉnh các giá trị của lượt Visits
        XYSeriesRenderer visitsRenderer = new XYSeriesRenderer();
        visitsRenderer.setColor(Color.RED);//Màu đỏ
        visitsRenderer.setPointStyle(PointStyle.CIRCLE);//Chấm tròm
        visitsRenderer.setFillPoints(true);//Đổ đầy chấm
        visitsRenderer.setLineWidth(2);//Độ rộng dòng
        visitsRenderer.setDisplayChartValues(true);//Cho phép hiển thị giá trị
 
        //Tạo XYSeriesRenderer để tùy chỉnh các giá trị của lượt Likes
        XYSeriesRenderer LikesRenderer = new XYSeriesRenderer();
        LikesRenderer.setColor(Color.GREEN);
        LikesRenderer.setPointStyle(PointStyle.CIRCLE);
        LikesRenderer.setFillPoints(true);
        LikesRenderer.setLineWidth(2);
        LikesRenderer.setDisplayChartValues(true);
 
        //Khởi tạo một đối tượng XYMultipleSeriesRenderer để tùy chỉnh biểu đồ theo ý muốn
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
 
        //Thiết lập title
        multiRenderer.setChartTitle("Visits vs Likes Chart");
        multiRenderer.setXTitle("Days");//Title trục X
        
        multiRenderer.setYAxisAlign(Align.RIGHT, 0);
        multiRenderer.setYLabelsAlign(Align.LEFT);//Chữ nằm về phía bên phải của cột
        
        multiRenderer.setXLabelsColor(Color.CYAN);//Màu sắc cho chữ trục X
        multiRenderer.setYTitle("Count");//Title trục Y
        multiRenderer.setZoomButtonsVisible(false);//Không cho phép zoom nút button
 
        // Thêm visitsRenderer and LikesRenderer vào multipleRenderer
        multiRenderer.addSeriesRenderer(visitsRenderer);
        multiRenderer.addSeriesRenderer(LikesRenderer);
 
        //Lấy đối tượng LinearLayout từ XML để sử dụng
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
 
        //Tạo biểu đồ
        mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, multiRenderer,"dd-MMM-yyyy");
 
        multiRenderer.setClickEnabled(true);//Cho phép click
        multiRenderer.setSelectableBuffer(10);//Thiết lập vùng đệm
 
        //Thiết lập một sự kiện lắng nghe từ giao diện (không cần đoạn này cũng đc)
        mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Format formatter = new SimpleDateFormat("dd-MMM-yyyy"); 
                SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint(); 
                if (seriesSelection != null) {
                    int seriesIndex = seriesSelection.getSeriesIndex();
                    String selectedSeries="Visits";
                    if(seriesIndex==0)
                        selectedSeries = "Visits";
                    else
                        selectedSeries = "Likes"; 
                    long clickedDateSeconds = (long) seriesSelection.getXValue();
                    Date clickedDate = new Date(clickedDateSeconds);
                    String strDate = formatter.format(clickedDate); 
                    int amount = (int) seriesSelection.getValue(); 
                    //Hiển thị toast
                    Toast.makeText(getBaseContext(), selectedSeries + " on "  + strDate + " : " + amount, Toast.LENGTH_SHORT).show();
                    }
                }
            });
 
            //Add cái biểu đồ này vào LinearLayout của xml
            chartContainer.addView(mChart);
    }
}
