package at.laptrinhgame;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.ui.activity.BaseGameActivity;




import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import at.laptrinhgame.R.menu;

public class MainActivity extends BaseGameActivity  {

	private static final int CAMERA_WIDTH = 480; // độ rộng màn hình hiển thị
	private static final int CAMERA_HEIGHT = 800; // chiều cao màn hình hiển thị
		
	 private Texture mFontTexture;
	// khai báo test
	private Texture texture;
	private Font font;	
	private Font mFont;
	private int count;
	
	
	// bai 4
	
	// PORTRAIT: khởi tạo màn hình xoay ngang! 
	// load game: kich thuoc camera, khung nhìn
	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub		
		
		  final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);  
		  
		  // nằm ngang : ScreenOrientation.PORTRAIT
		  final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);

		  engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		  
		  // khởi tạo biến
		  count = 0;
		  return new Engine(engineOptions);
	}
		

	// scene phương thức hiển thi chính!
	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
	
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0.23f, 1, 1));

		// load text
		Text text = new Text(200, 200, font, "Hello, AddEngine");		
		
		// gán lên màn hình
		scene.attachChild(text);
		
		Text textCustom = new Text(400,400, font, "chào moi ngườ!");
		scene.attachChild(textCustom);		
		
		// load text change
		// Kiểu changeableText -> có thể thay đổi giá trị textbox
		final ChangeableText textChange = new ChangeableText(200, 600, font, "Count = " + "+0+");
		scene.attachChild(textChange);
		final ChangeableText txtResult = new ChangeableText(10, 10, font, "Binggo");
		
		textChange.setRotation(90);
		CountDownTimer t = new CountDownTimer(30000, 100) { 

			// tổng thời gian là 30k,  sau 100s là tăng count 1 lần	
			@Override
			public void onTick(long milliUntilFinished) {

				// TODO Auto-generated method stub
				count ++;
				textChange.setText("Count = " + count);
				
				if (count == 50)
					scene.attachChild(txtResult);
						
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}
		};
		t.start();	// nhớ gọi hàm countdown mới chạy nhé
		
		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}

	// load các file nguồn như âm thanh hình ảnh
	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		// loadCustomFont();
		
		this.mFontTexture = new Texture(256,  256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);			
		this.mFont = new Font(mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.RED);
			
		this.mEngine.getTextureManager().loadTexture(mFontTexture);
		this.mEngine.getFontManager().loadFont(mFont);
		
		loadCustomFont();
		

		// load texture khi start game (scence)
	}
	
	private void loadFont()
	{	
		// load text
		
		// 256, 256:  kích thước lớn nhất mà textture có thể chứa trong nó, thông số này quan trọng
		// Typeface.DEFAULT , Typeface.BOLD: kiểu chữ arial mặc định
		// 32, true, Color.BLACK: Cỡ chữ 32 
		Texture mFontTexture = new Texture(256,  256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);			
		Font mFont = new Font(mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.RED);
			
		this.mEngine.getTextureManager().loadTexture(mFontTexture);
		this.mEngine.getFontManager().loadFont(mFont);			
	}
	
	private void loadCustomFont()
	{

		// 256, 256 mới ok nhé
		
		FontFactory.setAssetBasePath("font/");
		texture = new Texture(256,256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createFromAsset(this.texture, this, 
					"GLECB.TTF", 30, true, Color.RED);
		
		mEngine.getTextureManager().loadTexture(texture);		 
		mEngine.getFontManager().loadFont(font);
	}
}
