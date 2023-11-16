package tk0821.mahjong;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {

	@Override
	public boolean keyDown(int keycode) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if (button == Buttons.LEFT) {
			Mahjong.isClicked = true;
			Mahjong.mouseX = screenX;
			Mahjong.mouseY = screenY;
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
