package com.ra.cakepercent;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CakeView extends View {
	
	public float width;//�����ؼ��Ŀ�
	public float height;//�����ؼ��ĸ�
	private float realXSide = 0;//ʵ�ʱ�ͼ�Ŀ��������Ŀ�ȱ߽��ࡣ
	private float realYSide = 0;//ʵ�ʱ�ͼ�ĸ߶�������ĸ߶ȱ߽��ࡣ
	private float cakeWidth;
	private float cakeHeight;
	private float outXLength=0;//��x������ͻ���ľ���
	private float outYLength=0;//��y������ͻ���ľ���
	private int thickness=80;
	private float curAngle=0;//����ǵ������ߵĽǶȡ���3��Ϊ0��
	private float outXBaseLong;//x������ͻ���Ļ�׼����
	private float outYBaseLong;//y������ͻ���Ļ�׼����
	private float xCenter;//��ͻ���ı���draw��������
	private float yCenter;//��ͻ���ı���draw��������
	private float cake4HeightIndex=0;//��4���޵ĵ�����������y����
	private float cake3HeightIndex=0;//��3���޵ĵ�����������y����
	private float cake2HeightIndex=0;//��2���޵ĵ�����������y����
	private float cake1HeightIndex=0;//��1���޵ĵ�����������y����
	private float textSize;
	private int[] topColorStore=new int[]{Color.rgb(187,12,0),Color.rgb(137,220,47),Color.rgb(49,203,234),Color.rgb(204,7,220),Color.rgb(22,239,228),Color.rgb(142,185,210),Color.rgb(206,123,108),Color.rgb(230,201,235)};
	private int[] shadowColorStore=new int[]{Color.rgb(107,12,0),Color.rgb(57,140,47),Color.rgb(49,120,154),Color.rgb(143,5,154),Color.rgb(12,175,167),Color.rgb(67,131,169),Color.rgb(163,70,54),Color.rgb(186,106,200)};
	private ArrayList<infoPackage> infos=new ArrayList<infoPackage>();
	/**
	 * @param context ������
	 * @param colors ��������ɫ����
	 * @param shade_colors ��Ӱ��ɫ����
	 * @param percent �ٷֱ� (�ͱ�����360)
	 */
	public CakeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public void init(float width,float height,String[] names,int[] nums)
	{
		this.width=width;
		this.height=height;
		textSize=width/30;
		thickness=(int) (height/7.5f);
		initInfos(names,nums);
		realXSide=width/5;
		realYSide=height/7+thickness;
		cakeWidth=width-2*realXSide-(width-2*realXSide)/10;
		cakeHeight=height-2*realYSide-(height-2*realYSide)/10;
		outXBaseLong=cakeWidth/10;
		outYBaseLong=cakeHeight/10;
		outXLength=cakeWidth/10;
		outYLength=cakeHeight/10;
		
		
		
		RectF rectf=new RectF(realXSide, realYSide-thickness/2, realXSide+cakeWidth, realYSide+cakeHeight-thickness/2);
		xCenter=rectf.centerX();
		yCenter=rectf.centerY();
		this.invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		cake4HeightIndex=realYSide+cakeHeight+outYBaseLong+thickness-(realYSide*4f/3f);
		cake3HeightIndex=height-(height-(realYSide+cakeHeight+outYBaseLong+thickness))/3;
		cake2HeightIndex=realYSide-thickness+realYSide/2;
		cake1HeightIndex=(realYSide-thickness)/3;
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		
		for(int i=0;i<=thickness;i++){
			if(i!=thickness){
				drawOneCake(paint,canvas,i,false);
			}else
			{
				drawOneCake(paint,canvas,i,true);
			}
		}
		drawInfoMark(paint,canvas);
		
	}
	/**���ݱ���ָ��ĽǶȻ�ȡ�ñ���ͻ����outXLength��outYLength*/
	private void getoutXY(float curAngle)
	{	//�������޵����
		if(curAngle>=0&&curAngle<45)
		{
			outXLength=outXBaseLong;
			outYLength=(outYBaseLong)*curAngle/45;
		}else if(curAngle>=45&&curAngle<90)
		{
			outXLength=(outXBaseLong)*(90-curAngle)/45;
			outYLength=outYBaseLong;
		}
		//�������޵����
		if(curAngle>=90&&curAngle<135)
		{
			outXLength=-(outXBaseLong)*(curAngle-90)/45;
			outYLength=outYBaseLong;
		}
		else if(curAngle>=135&&curAngle<180)
		{
			outXLength=-outXBaseLong;
			outYLength=(outYBaseLong)*(180-curAngle)/45;
		}
		//�ڶ����޵����
		if(curAngle>=180&&curAngle<225)
		{
			outXLength=-outXBaseLong;
			outYLength=-(outYBaseLong)*(curAngle-180)/45;
		}
		else if(curAngle>=225&&curAngle<270)
		{
			outXLength=-(outXBaseLong)*(270-curAngle)/45;
			outYLength=-outYBaseLong;
		}
		//��һ���޵����
		if(curAngle>=270&&curAngle<315)
		{
			outXLength=(outXBaseLong)*(curAngle-270)/45;
			outYLength=-outYBaseLong;
		}
		else if(curAngle>=315&&curAngle<360)
		{
			outXLength=outXBaseLong;
			outYLength=-(outYBaseLong)*(360-curAngle)/45;
		}
	}
	/** ��һ�ű���*/
	private void drawOneCake(Paint paint,Canvas canvas,int i,boolean isRoof)
	{
		float tempAngle=0;
		for(int j=0;j<infos.size();j++){
			if(j!=infos.size()-1)
			{
				curAngle=tempAngle+infos.get(j).getPercent()/2f;
				getoutXY(curAngle);
				if(isRoof==false)
					paint.setColor(infos.get(j).getShadowColors());
				else
					paint.setColor(infos.get(j).getTopColors());
				RectF rectf=new RectF(realXSide+outXLength, realYSide+thickness/2-i+outYLength, realXSide+cakeWidth+outXLength, realYSide+cakeHeight+thickness/2-i+outYLength);
				canvas.drawArc(rectf, tempAngle,infos.get(j).getPercent(), true, paint);
				recordDrawPoint(outXLength,outYLength,tempAngle,infos.get(j).getPercent(),j,i);
			}
			else
			{//���һ���ǲ�ͻ��
				if(isRoof==false)
					paint.setColor(infos.get(j).getShadowColors());
				else
					paint.setColor(infos.get(j).getTopColors());
				outXLength=0;
				outYLength=0;
				RectF rectf=new RectF(realXSide+outXLength, realYSide+thickness/2-i+outYLength, realXSide+cakeWidth+outXLength, realYSide+cakeHeight+thickness/2-i+outYLength);
				canvas.drawArc(rectf, tempAngle,infos.get(j).getPercent(), true, paint);
				curAngle=tempAngle+infos.get(j).getPercent()/2;
				getoutXY(curAngle);
				recordDrawPoint(outXLength,outYLength,tempAngle,infos.get(j).getPercent(),j,i);
			}
			tempAngle+=infos.get(j).getPercent();
		}
	}
	/** ��¼������ǵ����ĵ㵽infoPackage��*/
	private void recordDrawPoint(float outXLength,float outYLength,float tempAngle,float percent,int j,int i)
	{
		float infoPointX;
		float infoPointY;
		if(i==thickness)
		{
			//����ĵ��
			float x=(float) (xCenter+(cakeWidth/2.3)*(Math.cos(curAngle*3.1415926/180)));
			float y=(float) (yCenter+(cakeHeight/2.3)*(Math.sin(curAngle*3.1415926/180)));
			infos.get(j).setxDraw(x);
			infos.get(j).setyDraw(y);
		}
	}
	/** ����Ϣ��Ǻ���*/
	private void drawInfoMark(Paint paint,Canvas canvas)
	{
		paint.setColor(Color.WHITE);
		paint.setTextSize(textSize);
		for (int i = 0; i < infos.size(); i++) {
			if(infos.get(i).getxDraw()>=xCenter&&infos.get(i).getyDraw()>=yCenter)
			{//��������
				Rect rect = new Rect();
				float textHeight;
				float cakeRightSide=realXSide+cakeWidth+outXBaseLong;//�����Ҳ�x����
				paint.getTextBounds(infos.get(i).getName(), 0, infos.get(i).getName().length(), rect);  
				textHeight=rect.height();
				canvas.drawText(infos.get(i).getName(), cakeRightSide+realXSide/5, cake4HeightIndex+(textHeight+textHeight/2), paint);
				canvas.drawLine(infos.get(i).getxDraw(), infos.get(i).getyDraw(), (cakeRightSide+realXSide/5-textHeight/2), cake4HeightIndex-textHeight/2+(textHeight+textHeight/2), paint);
				cake4HeightIndex+=(textHeight+textHeight/2);
			}
			else if(infos.get(i).getxDraw()<xCenter&&infos.get(i).getyDraw()>=yCenter)
			{//��������
				Rect rect = new Rect();
				float textHeight;
				float textWidth;
				float cakeLeftSide=realXSide;//�������x����
				paint.getTextBounds(infos.get(i).getName(), 0, infos.get(i).getName().length(), rect);  
				textHeight=rect.height();
				textWidth=rect.width();
				canvas.drawText(infos.get(i).getName(), cakeLeftSide-realXSide/5-textWidth, cake3HeightIndex-(textHeight+textHeight/2), paint);
				canvas.drawLine(infos.get(i).getxDraw(), infos.get(i).getyDraw(), (cakeLeftSide-realXSide/5+textHeight/2), cake3HeightIndex-textHeight/2-(textHeight+textHeight/2), paint);
				cake3HeightIndex-=(textHeight+textHeight/2);
			}
			else if(infos.get(i).getxDraw()<xCenter&&infos.get(i).getyDraw()<yCenter)
			{//�ڶ�����
				
				Rect rect = new Rect();
				float textHeight;
				float textWidth;
				float cakeLeftSide=realXSide;//�������x����
				paint.getTextBounds(infos.get(i).getName(), 0, infos.get(i).getName().length(), rect);  
				textHeight=rect.height();
				textWidth=rect.width();
				canvas.drawText(infos.get(i).getName(), cakeLeftSide-realXSide/3-textWidth, cake2HeightIndex-(textHeight+textHeight/2), paint);
				canvas.drawLine(infos.get(i).getxDraw(), infos.get(i).getyDraw(), (cakeLeftSide-realXSide/3+textHeight/2), cake2HeightIndex-textHeight/2-(textHeight+textHeight/2), paint);
				cake2HeightIndex-=(textHeight+textHeight/2);
			}
			else if(infos.get(i).getxDraw()>=xCenter&&infos.get(i).getyDraw()<yCenter)
			{//��һ����
				
				Rect rect = new Rect();
				float textHeight;
				float cakeRightSide=realXSide+cakeWidth+outXBaseLong;//�����Ҳ�x����
				paint.getTextBounds(infos.get(i).getName(), 0, infos.get(i).getName().length(), rect);  
				textHeight=rect.height();
				canvas.drawText(infos.get(i).getName(), cakeRightSide+realXSide/5, cake1HeightIndex+(textHeight+textHeight/2), paint);
				canvas.drawLine(infos.get(i).getxDraw(), infos.get(i).getyDraw(), (cakeRightSide+realXSide/5-textHeight/2), cake1HeightIndex-textHeight/2+(textHeight+textHeight/2), paint);
				cake1HeightIndex+=(textHeight+textHeight/2);
			}
			
		}
		
	}
	
	private void initInfos(String[] names,int[] nums)
	{
		//��ʼ��infos
		float[] percents=new float[names.length];
		int numSum=0;
		for (int i = 0; i < nums.length; i++) {
			numSum+=nums[i];
		}
		for (int i = 0; i < nums.length; i++) {
			percents[i]=((float)nums[i]/(float)numSum)*360f;
		}
		//�Զ�������ɫ
		int[] topColors=new int[names.length];
		int[] shadowColors=new int[names.length];
		for (int i = 0; i < names.length; i++) {
//			int r=(int) (50+200*(new Random().nextFloat()));
//			int g=(int) (50+200*(new Random().nextFloat()));
//			int b=(int) (50+200*(new Random().nextFloat()));
			int index=i;
			index=index%topColorStore.length;
			topColors[i]=topColorStore[index];
			shadowColors[i]=shadowColorStore[index];
		}
		infos.clear();
		for (int i = 0; i < names.length; i++) {
			infos.add(new infoPackage(-1,-1,topColors[i], shadowColors[i], names[i], nums[i], percents[i]));
		}
		//����infos�е�percents ��infos��������
		doBubbleSort(infos);
	}
	private void doBubbleSort(ArrayList<infoPackage> infos)
	{
		int len=infos.size();
		for (int i = 0; i < len; i++) {
			for (int j = i+1; j < len; j++) {
				infoPackage temp;
				if(infos.get(i).getPercent()>infos.get(j).getPercent())
				{
					temp=infos.get(j);
					infos.set(j, infos.get(i));
					infos.set(i, temp);
				}
			}
		}
	}
	private class infoPackage
	{
		private float xDraw;//��ͼ�������Ϣ������Ļ�������x
		private float yDraw;//��ͼ�������Ϣ������Ļ�������y
		private String name;
		private int num;
		private int topColors;
		private int shadowColors;
		private float percent;
		public infoPackage(float xDraw, float yDraw, int topColors,int shadowColors,String name, int num,float percent) {
			super();
			this.xDraw = xDraw;
			this.yDraw = yDraw;
			this.topColors=topColors;
			this.shadowColors=shadowColors;
			this.name = name;
			this.num = num;
			this.percent=percent;
		}
		public int getTopColors() {
			return topColors;
		}
		public void setTopColors(int topColors) {
			this.topColors = topColors;
		}
		public int getShadowColors() {
			return shadowColors;
		}
		public void setShadowColors(int shadowColors) {
			this.shadowColors = shadowColors;
		}
		public float getxDraw() {
			return xDraw;
		}
		public void setxDraw(float xDraw) {
			this.xDraw = xDraw;
		}
		public float getyDraw() {
			return yDraw;
		}
		public void setyDraw(float yDraw) {
			this.yDraw = yDraw;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public float getPercent() {
			return percent;
		}
		public void setPercent(float percent) {
			this.percent = percent;
		}
	}
}
