package kr.co.teada.ex75cameratestapi;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;

    //카메라 하드웨어 객체의 참조변수
    //minimum API 21 버전 이상인 앱으로 개발 할 때는 CameraDevice(camera2) 사용 가능
    Camera camera;


    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder=getHolder();
        holder.addCallback(this);

    }//end of Constructor(생성자)

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //이 때부터 미리보기 화면(surface)를 만들 수 있어
        //카메라 하드웨어의 셔터 열어
        camera=Camera.open(0); //0: back camera || 1:front camera  -> camera ID

        //카메라가 open 되면 취득한 정보를 지속적으로 전달해 줌
        //이를 미리보기로 surface 에 보여줘야 해 (아직 찍은게 아니야. 일단 카메라 켠거야(미리보기를 서비스뷰에 보여주는겨))
        try {
            //미리보기 화면 설정
            camera.setPreviewDisplay(holder);
            //카메라는 무조건 가로 방향이야
            //세로로 보고 싶다면 ..90도 회전
            camera.setDisplayOrientation(90);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //서비스 사이즈 결정돼 : 미리보기 시작! : 이 때부터 홀더에게 정보 줘
        camera.startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //미리보기 종료
        camera.stopPreview();

        //카메라 닫기
        camera.release(); //메모리 제거
        camera=null; //가비지 컬렉터야 가져가

    }


}//end of CameraView
