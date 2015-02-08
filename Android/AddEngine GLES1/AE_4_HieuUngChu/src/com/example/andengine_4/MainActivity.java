package com.example.andengine_4;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleAtModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.modifier.ParallelBackgroundModifier;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.text.TickerText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 480; // độ rộng màn hình hiển thị
	private static final int CAMERA_HEIGHT = 800; // chiều cao màn hình hiển thị

	private Font mFontTicker;
	private Texture mFontTextureTicker;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub

		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// nằm ngang : ScreenOrientation.PORTRAIT
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);

		engineOptions.getTouchOptions().setRunOnUpdateThread(true);

		// khởi tạo biến
		return new Engine(engineOptions);
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub

		this.mFontTextureTicker = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontTicker = new Font(this.mFontTextureTicker, Typeface.create(
				Typeface.SANS_SERIF, Typeface.BOLD_ITALIC), 25, true, Color.RED);

		this.mEngine.getTextureManager().loadTexture(mFontTextureTicker);
		this.mEngine.getFontManager().loadFont(mFontTicker);

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub

		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0.18f, 2, 3));

		final Text textticker = new TickerText(10, 450, this.mFontTicker,
				"Good luck!", HorizontalAlign.CENTER, 20);

		// đăng ký hiệu ứng text

		// ParallelBackgroundModifier -> đọc từng từ trong text
		// trong đó có 2 hiệu ứng nhỏ là:
		// alpha: là hiện dần text từ mờ tới rõ nét trong 10s, từ ko màu 0,0 ->
		// 1.0
		// sale.. phóng to text với 10s, từ cỡ 0.5 cho tới cỡ bình thường 1.0

		textticker.registerEntityModifier(new SequenceEntityModifier(
				new ParallelEntityModifier(new AlphaModifier(10, 0.0f, 1.0f),
						new ScaleModifier(10, 0.5f, 1.0f)),
				new RotationModifier(5, 0, 360)));

		textticker.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);

		scene.attachChild(textticker);
		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}
