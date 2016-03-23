package com.example.thenextdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * @author cloud
 *
 */
/**
 * @author cloud
 *
 */
/**
 * @author cloud
 *
 */
/**
 * @author cloud
 *
 */

public class MainActivity extends Activity {

	TextView textView4,textView5,textView6,textView7;
	EditText editText1,editText2,editText3;
	Button mButton;
	List<Map<String,String>>mList;
	int distanceDay=0;
	int info;
	int springDay = 0;
	int springMonth = 0;
	int lunarMonth=0;
	int isLunarRain = 0;//存放闰月月份没有的话为0
	//使用比特位记录每年的情况
	//0~4 共5bit 春节日份
	//5~6 共2bit 春节月份
	//7~19 共13bit 13个月的大小月情况(如果无闰月，最后位无效)，大月为1,小月为0
	//20~23 共4bit 记录闰月的月份，如果没有闰月为0      
	int[] lunar=new int[]{
			 0x04AE53,0x0A5748,0x5526BD,0x0D2650,0x0D9544,
		     0x46AAB9,0x056A4D,0x09AD42,0x24AEB6,0x04AE4A, //1901-1910

		     0x6A4DBE,0x0A4D52,0x0D2546,0x5D52BA,0x0B544E,
		     0x0D6A43,0x296D37,0x095B4B,0x749BC1,0x049754, //1911-1920

		     0x0A4B48,0x5B25BC,0x06A550,0x06D445,0x4ADAB8,
		     0x02B64D,0x095742,0x2497B7,0x04974A,0x664B3E, //1921-1930

		     0x0D4A51,0x0EA546,0x56D4BA,0x05AD4E,0x02B644,
		     0x393738,0x092E4B,0x7C96BF,0x0C9553,0x0D4A48, //1931-1940

		     0x6DA53B,0x0B554F,0x056A45,0x4AADB9,0x025D4D,
		     0x092D42,0x2C95B6,0x0A954A,0x7B4ABD,0x06CA51, //1941-1950

		     0x0B5546,0x555ABB,0x04DA4E,0x0A5B43,0x352BB8,
		     0x052B4C,0x8A953F,0x0E9552,0x06AA48,0x7AD53C, //1951-1960

		     0x0AB54F,0x04B645,0x4A5739,0x0A574D,0x052642,
		     0x3E9335,0x0D9549,0x75AABE,0x056A51,0x096D46, //1961-1970

		     0x54AEBB,0x04AD4F,0x0A4D43,0x4D26B7,0x0D254B,
		     0x8D52BF,0x0B5452,0x0B6A47,0x696D3C,0x095B50, //1971-1980

		     0x049B45,0x4A4BB9,0x0A4B4D,0xAB25C2,0x06A554,
		     0x06D449,0x6ADA3D,0x0AB651,0x093746,0x5497BB, //1981-1990

		     0x04974F,0x064B44,0x36A537,0x0EA54A,0x86B2BF,
		     0x05AC53,0x0AB647,0x5936BC,0x092E50,0x0C9645, //1991-2000

		     0x4D4AB8,0x0D4A4C,0x0DA541,0x25AAB6,0x056A49,
		     0x7AADBD,0x025D52,0x092D47,0x5C95BA,0x0A954E, //2001-2010

		     0x0B4A43,0x4B5537,0x0AD54A,0x955ABF,0x04BA53,
		     0x0A5B48,0x652BBC,0x052B50,0x0A9345,0x474AB9, //2011-2020

		     0x06AA4C,0x0AD541,0x24DAB6,0x04B64A,0x69573D,
		     0x0A4E51,0x0D2646,0x5E933A,0x0D534D,0x05AA43, //2021-2030

		     0x36B537,0x096D4B,0xB4AEBF,0x04AD53,0x0A4D48,
		     0x6D25BC,0x0D254F,0x0D5244,0x5DAA38,0x0B5A4C, //2031-2040

		     0x056D41,0x24ADB6,0x049B4A,0x7A4BBE,0x0A4B51,
		     0x0AA546,0x5B52BA,0x06D24E,0x0ADA42,0x355B37, //2041-2050

		     0x09374B,0x8497C1,0x049753,0x064B48,0x66A53C,
		     0x0EA54F,0x06B244,0x4AB638,0x0AAE4C,0x092E42, //2051-2060

		     0x3C9735,0x0C9649,0x7D4ABD,0x0D4A51,0x0DA545,
		     0x55AABA,0x056A4E,0x0A6D43,0x452EB7,0x052D4B, //2061-2070

		     0x8A95BF,0x0A9553,0x0B4A47,0x6B553B,0x0AD54F,
		     0x055A45,0x4A5D38,0x0A5B4C,0x052B42,0x3A93B6, //2071-2080

		     0x069349,0x7729BD,0x06AA51,0x0AD546,0x54DABA,
		     0x04B64E,0x0A5743,0x452738,0x0D264A,0x8E933E, //2081-2090

		     0x0D5252,0x0DAA47,0x66B53B,0x056D4F,0x04AE45,
		     0x4A4EB9,0x0A4D4C,0x0D1541,0x2D92B5 //2091-2099
	};
	String[] nYear=new String[]{
			"甲子",
			"乙丑",
			"丙寅",
			"丁卯",
			 "戊辰",
			"己巳",
			"庚午",
			"辛未",
			"壬申",
			"癸酉",
			"甲戌",
			"乙亥",
			"丙子",
			"丁丑",
			"戊寅",
			"己卯",
			"庚辰",
			"辛巳",
			"壬午",
			"癸未",
			"甲申",
			"乙酉",
			"丙戌",
			"丁亥",
			"戊子",
			"己丑",
			"庚寅",
			"辛卯",
			"壬辰",
			"癸巳",
			"甲午",
			"乙未",
			"丙申",
			"丁酉",
			"戊戌",
			"己亥",
			"庚子",
			"辛丑",
			"壬寅",
			"癸卯",
			"甲辰",
			"乙巳",
			"丙午",
			"丁未",
			"戊申",
			"己酉",
			"庚戌",
			"辛亥",
			"壬子",
			"癸丑",
			"甲寅",
			"乙卯",
			"丙辰",
			"丁巳",
			"戊午",
			"己未",
			"庚申",
			"辛酉",
			"壬戌",
			"癸亥"
	};
	private TextView textView8,textView9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mList=new ArrayList<Map<String,String>>();
		initview();//初始化控件
		setListener();	//监听button时间 点击获取下一天
	}


	private void setListener() {
		// TODO Auto-generated method stub
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					getCalinder();//获取日历
					getWeek();//获取星期
					distanceDay=0;//初始化解析的农历数据
					info=0;
					springDay = 0;
					springMonth = 0;
					lunarMonth=0;
					isLunarRain = 0;
			}

			
		});
	}
	/**
	 * @author cloud
	 *无参数
	 *使ui设定星期
	 */
	private void getWeek() {
		String year=editText1.getText().toString();
		String month=editText2.getText().toString();
		String day=editText3.getText().toString();
		int mYear=Integer.parseInt(year);
		int mMonth=Integer.parseInt(month);
		int mDay=Integer.parseInt(day);
		int mDistanceDay=0;
		for(int i=1900;i<=(mYear-1);i++)//计算年跨度
		{
			if(isRain(i))
			{
				mDistanceDay=mDistanceDay+366;
			}else {
				mDistanceDay=mDistanceDay+365;
			}
		}
		for(int i=1;i<=(mMonth-1);i++)
		{
			if(i==2)
			{
				if(isRain(mYear))
				{
					mDistanceDay=mDistanceDay+29;
				}else {
					mDistanceDay=mDistanceDay+28;
				}
			}else if(i==1||i==3||i==7||i==8||i==10||i==12)
			{
				mDistanceDay=mDistanceDay+31;
			}else {
				mDistanceDay=mDistanceDay+30;
			}
		}
		mDistanceDay=mDistanceDay+mDay;//算出总间隔
		int yu=(mDistanceDay+1)%7;//算出余几天
		if(yu!=0)
		textView9.setText("下一天是星期"+yu);
		else {
			{
				textView9.setText("下一天是星期日");
			}
		}
	}
	/**
	 * @author cloud
	 *无参数
	 *获取日历
	 */	
private void getCalinder() {
		int year = 0,month=0,day=0;
		String mYear,mMonth,mDay;
		mYear=editText1.getText().toString();
		mMonth=editText2.getText().toString();
		mDay=editText3.getText().toString();
		if(TextUtils.isEmpty(mYear))
		{
			editText1.setError("请输入年份");
		}
		if(TextUtils.isEmpty(mMonth))
		{
			editText1.setError("请输入月份");
		}
		if(TextUtils.isEmpty(mMonth))
		{
			editText1.setError("请输入日期");
		}
		try {
			year=Integer.parseInt(mYear);
			month=Integer.parseInt(mMonth);
			day=Integer.parseInt(mDay);
		} catch (NumberFormatException e) {
			editText1.setError("请输入正确年份");
			editText2.setError("请输入正确月份");
			editText3.setError("请输入正确日期");
		}
		if(month<0||month>12||year>2100||year<1900)
		{
			editText1.setError("请输入正确年份");
			editText2.setError("请输入正确月份");
		}
		if(isRain(year))	//判断是否是闰年判断出输入天数是否正确
		{
			if(month==2)
			{
				if(day>29||day<1)
				{
					editText3.setError("请输入正确日期");
				}
			}else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
			{
				if(day<1||day>31)
				{
					editText3.setError("请输入正确日期");
				}
			}else {
				if(day>30||day<1)
				{
					editText3.setError("请输入正确日期");
				}
			}
		}else {//判断大月的天数输入是否正确
			if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
			{
				if(day<1||day>31)
				{
					editText3.setError("请输入正确日期");
				}
			}else if(month==2)
			{
				if(day>28||day<1)
				{
					editText3.setError("请输入正确日期");
				}
			}else {
				if(day>30||day<1)
				{
					editText3.setError("请输入正确日期");
				}
			}
		}
		if(month==2)//判断2月份下一天
		{
			if(isRain(year))
			{
				if(day==28)
				{
					day=29;
					textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
					getLunarCalender(year,month,day);
				}
				else if(day==29)
				{
					day=1;
					month=3;
					textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
					getLunarCalender(year,month,day);
				}else {
					day++;
					textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
					getLunarCalender(year,month,day);
				}
			}else {
				if(day==28)
				{
					day=1;
					month=3;
					textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
					getLunarCalender(year,month,day);//获取下一天农历
				}else {
					day++;
					textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
					getLunarCalender(year,month,day);//获取下一天农历
				}
			}
		}else if(month==12)//判断12月份的下一天
		{
			if(day==31)
			{
				year++;
				day=1;
				month=1;
				textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
			}
			else {
				day++;textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
			}
		}else if(month==1||month==3||month==5||month==7||month==8||month==10)
		{	
			if(day==31){month++; day=1; textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");}
			else{
				day++;textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");				
			}getLunarCalender(year,month,day);//获取下一天农历
		}else {
			if(day==30){month++; day=1; textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");}
			else{
				day++;textView7.setText("下一天是"+year+"年"+month+"月"+day+"天");
			}getLunarCalender(year,month,day);
		}
	}
/**
 * @author cloud
 *参数year month day int型为所求下一天
 *获取阴历
 */
	private void getLunarCalender(int year, int month, int day) {
		if(year==1900||year==2100)
		{
			textView8.setText("农历数据不足,缺少1900数据");
			return;
		}
		if(year==1901)
		{
			if(month<=2)
			{
				if(day<=18)
				{
					textView8.setText("农历数据不足,缺少1901年2月18号之前数据");
					return;
				}
			}
		}
		
		String lunarYear;
		distanceDay=0;
		 info=0;
		 springDay = 0;
		 springMonth = 0;
		 lunarMonth=0;
		 isLunarRain = 0;
		 int nyear;
		nyear=year%60-3;
		if(nyear<=0)
		{
			nyear=nyear+60;
		}
		lunarYear=nYear[nyear-1];	//获取农历年份	
		anlanize(year);//取出春节等日期
		distanceDay=getdistanceDay(springMonth,month,springDay,day,year);
		if(distanceDay<0)
		{
			lunarYear=nYear[nyear-2];//获得前一年农历
			anlanize(year-1);
			//System.out.println("取错了"+springDay+"d"+springMonth+"d"+lunarMonth+lunarYear+isLunarRain);

			getLunarDay(distanceDay,lunarYear);//获得年前农历
		}else if (distanceDay==0) {
			textView8.setText("农历是："+"初1"+lunarYear+"正月");
			//System.out.println("distance"+springDay+"f"+springMonth+"d"+lunarMonth+lunarYear+isLunarRain);
		}
		else {
			getSunDay(distanceDay,lunarYear);//获取年后农历
		}
			
			
			
			
		
		//System.out.println("frfrf"+springDay+"f"+springMonth+"d"+lunarMonth+lunarYear+isLunarRain);
	}
	/**
	 * @author cloud
	 *获取该年大年初一后的农历
	 *参数相距大年初一天数 农历年份
	 */
	private void getSunDay(int distanceDay2, String lunarYear) {
		int[] j = new int[14];//存放当前每月天数
		int tempMonth=lunarMonth;
		int tempDay=0;
		int sum=0;
		int count=0;//计算推了几个月
		int distanceTemp=distanceDay2;
			for(int i=1;i<=13;i++)
			{					
				j[14-i]=(tempMonth>>(i-1))&1;			
			}
		if(distanceDay2%30!=0)
		{
			count=(distanceDay2/30)+1;
		}else count=distanceDay2/30;
		if(isLunarRain==0)
		{
			for(int i=1;i<=12;i++)
			{
				
				if(j[i]==0)
				{
					sum=sum+29;
				}else {
					{
						sum=sum+30;
					}
				}
				if(sum>=distanceDay2)
				{
					if(j[i]==0)
					{
						tempDay=29-(sum-distanceDay2)+1;
						if(tempDay==30)
						{
							tempDay=1;
							textView8.setText("农历："+"第"+(i+1)+"月"+"第"+tempDay+"天");
							return;
						}
					}else {
						tempDay=30-(sum-distanceDay2)+1;
						if(tempDay==31)
						{
							tempDay=1;
							textView8.setText("农历："+"第"+(i+1)+"月"+"第"+tempDay+"天");
							return;
						}
					}
					textView8.setText("农历："+lunarYear+"第"+i+"月"+"第"+tempDay+"天");
					break;
				}
			}
		}
		else {
			if(isLunarRain<count)
			{
				for(int i=1;i<=count-1;i++)
				{
					if(j[i]==0)
					{
						sum=sum+29;
					}else 
						{
							sum=sum+30;
						}
				}
				if(j[13]==0)
				{
					sum=sum+29;
				}else 
					{
						sum=sum+30;
					}
				if(j[count-1]==0)
				{
					tempDay=29-(sum-distanceDay2)+1;
					if(tempDay==30)
					{
						tempDay=1;
						textView8.setText("农历："+"第"+count+"月"+"第"+tempDay+"天");
						return;
					}
				}else {
					tempDay=30-(sum-distanceDay2)+1;
					if(tempDay==30)
					{
						tempDay=1;
						textView8.setText("农历："+"第"+count+"月"+"第"+tempDay+"天");
						return;
					}
				}
				textView8.setText("农历："+"第"+(count-1)+"月"+"第"+tempDay+"天");
			}else {
				for(int i=1;i<=12;i++)
				{
					
					if(j[i]==0)
					{
						sum=sum+29;
					}else {
						{
							sum=sum+30;
						}
					}
					if(sum>=distanceDay2)
					{
						if(j[i]==0)
						{
							tempDay=29-(sum-distanceDay2)+1;
							if(tempDay==30)
							{
								tempDay=1;
								textView8.setText("农历："+"第"+(i+1)+"月"+"第"+tempDay+"天");
								return;
							}
						}else {
							tempDay=30-(sum-distanceDay2)+1;
							if(tempDay==31)
							{
								tempDay=1;
								textView8.setText("农历："+"第"+(i+1)+"月"+"第"+tempDay+"天");
								return;
							}
						}
						textView8.setText("农历："+"第"+i+"月"+"第"+tempDay+"天");
						break;
					}
				}
			}
		}
		
	}

	/**
	 * @author cloud
	 *获取该年大年初一前的农历
	 *参数相距大年初一天数 农历年份
	 */
	private void getLunarDay(int distanceDay2, String lunarYear) {
	
		int[] j = new int[14];//存放当前每月天数
		int tempMonth=lunarMonth;
		int tempDay=0;
		int count=0;//计算推了几个月
		int distanceTemp=distanceDay2;
			for(int i=1;i<=13;i++)
			{	
				
				j[14-i]=(tempMonth>>(i-1))&1;			
			}
			if(isLunarRain==0)
			{
				for(int i=1;i<=12;i++)
				{	count++;
					if(j[13-i]==1)
					{
						tempDay=30;
					}else{tempDay=29;}
					distanceTemp=distanceDay2+tempDay;
					if(distanceTemp>=0)
					{
						if(count==1)
						{
							if(j[12]==1)
							{	textView8.setText("农历是："+"初"+(30+distanceDay2+1)+lunarYear+"腊月");
								
							}else{textView8.setText("农历是："+"初"+(29+distanceDay2+1)+lunarYear+"腊月");}
						}else if(count==2)
						{
							if(j[12]==1)
							{
								if(j[11]==1)
								{	textView8.setText("农历是："+"初"+(60+distanceDay2+1)+lunarYear+"冬月");
								
								}else{textView8.setText("农历是："+"初"+(59+distanceDay2+1)+lunarYear+"冬月");}
							}else{
								if(j[11]==1)
								{	textView8.setText("农历是："+"初"+(59+distanceDay2+1)+lunarYear+"冬月");
			
								}else{textView8.setText("农历是："+"初"+(58+distanceDay2+1)+lunarYear+"冬月");}
							}
						}
						break;
					}
				}
			}else {
				if(isLunarRain==12)
				{	if(j[13]==1)
					{
					if(distanceDay2+30>=0)
				{	textView8.setText("农历是："+"初"+(30+distanceDay2+1)+lunarYear+"腊月");
					
				}if(distanceDay2+30<0)
				{
					if(j[12]==1){textView8.setText("农历是："+"初"+(60+distanceDay2+1)+lunarYear+"腊月");
																		}
					else{textView8.setText("农历是："+"初"+(59+distanceDay2+1)+lunarYear+"腊月");
						}
				}
					}else
					{
						if(distanceDay2+29>=0)
						{	textView8.setText("农历是："+"初"+(29+distanceDay2+1)+lunarYear+"腊月");
						
						}if(distanceDay2+29<0)
						{
							if(j[12]==1){textView8.setText("农历是："+"初"+(59+distanceDay2+1)+lunarYear+"腊月");
								}
							else{textView8.setText("农历是："+"初"+(58+distanceDay2+1)+lunarYear+"腊月");
								}
						}
					}						
				}//与判断12月润对应				
				if(isLunarRain==11)
				{
					if(j[12]==1)
					{
					if(distanceDay2+30>=0)
				{	textView8.setText("农历是："+"初"+(30+distanceDay2+1)+lunarYear+"腊月");
					//System.out.println("初"+(30+distanceDay2+1)+lunarYear+"腊月");
				}if(distanceDay2+30<0)
				{
					if(j[13]==1){textView8.setText("农历是："+"初"+(60+distanceDay2+1)+lunarYear+"冬月");
						//System.out.println("初"+(60+distanceDay2+1)+lunarYear+"冬月");
					}
					else{
						textView8.setText("农历是："+"初"+(59+distanceDay2+1)+lunarYear+"冬月");	
				//System.out.println("初"+(59+distanceDay2+1)+lunarYear+"冬月");}
					}
				}
					
				}else
				{
					if(distanceDay2+29>=0)
					{	textView8.setText("农历是："+"初"+(29+distanceDay2+1)+lunarYear+"腊月");
						//System.out.println("初"+(29+distanceDay2+1)+lunarYear+"腊月");
					}if(distanceDay2+29<0)
					{
						if(j[13]==1){System.out.println("初"+(59+distanceDay2+1)+lunarYear+"冬月");}
						else{System.out.println("初"+(58+distanceDay2+1)+lunarYear+"冬月");}
					}
				}	
				}	
				if(isLunarRain!=11||isLunarRain!=12)
				{  count=0;
					for(int i=1;i<=12;i++)
					{	count++;
						if(j[13-i]==1)
						{
							tempDay=30;
						}else{tempDay=29;}
						distanceTemp=distanceTemp+tempDay;
						if(distanceTemp>=0)
						{
							if(count==1)
							{
								if(j[12]==1)
								{	textView8.setText("农历是："+"初"+(30+distanceDay2+1)+lunarYear+"腊月");
									
								}else{textView8.setText("农历是："+"初"+(29+distanceDay2+1)+lunarYear+"腊月");}
							}else if(count==2)
							{
								if(j[12]==1)
								{
									if(j[11]==1)
									{	textView8.setText("农历是："+"初"+(60+distanceDay2+1)+lunarYear+"冬月");
									
									}else{textView8.setText("农历是："+"初"+(59+distanceDay2+1)+lunarYear+"冬月");
										//System.out.println("初"+(59+distanceDay2+1)+lunarYear+"冬月");
									}
								}else{
									if(j[11]==1)
									{	textView8.setText("农历是："+"初"+(59+distanceDay2+1)+lunarYear+"冬月");
										//System.out.println("初"+(59+distanceDay2+1)+lunarYear+"冬月");
									}else{textView8.setText("农历是："+"初"+(58+distanceDay2+1)+lunarYear+"冬月");
										//System.out.println("初"+(58+distanceDay2+1)+lunarYear+"冬月");
									}
								}
							}
							break;
						}
					}
				}
				
			}
		
	}
	/**
	 * @author cloud
	 *获取相聚大年初一天数
	 */

	private int getdistanceDay(int springMonth2, int month, int springDay2, int day, int year) {
		// 获得距离天数
		if(month<=springMonth)
		{distanceDay=0;
		if(springMonth-month==2)
		{   if(isRain(year))
			distanceDay=31+29+springDay-day;
		else {
			distanceDay=31+28+springDay-day;
		}
		}else if(springMonth-month==1)
		{
			if(springMonth2==3)
			{
				if(isRain(year))
					distanceDay=29+springDay-day;
				else{distanceDay=28+springDay-day;}
			}else if(springMonth2==2){
				distanceDay=31+springDay-day;			}		
			
		}else{	if(day<springDay2)
		{
			distanceDay=springDay2-day;
		}	else	{
			return day-springDay2;
			}	
			}
		return -distanceDay;//在前面返回负数
		}
		else {
			distanceDay=0;
			for(int i=springMonth2;i<=month-1;i++)
			{	
				if(i==1||i==3||i==5||i==7||i==8||i==10||i==12)
				{
					distanceDay=distanceDay+31;
				}else if(i==2)
				{
					if(isRain(year)){distanceDay=distanceDay+29;}
					else{
						distanceDay=distanceDay+28;
					}
				}else{distanceDay=distanceDay+30;}
				
				
			}
			
				distanceDay=distanceDay-springDay+day;
				return distanceDay;
										
		}
		
	}

	/**
	 * @author cloud
	 *参数年份 解析出概念农历信息
	 */
	private void anlanize(int year) {
		// TODO Auto-generated method stub
		
		 info=lunar[year-1901];
		 springDay=info&(31);
		 springMonth=(info>>5)&(3);
		 lunarMonth=(info>>7)&(8191);
		 isLunarRain=(info>>20)&(15);
		 System.out.println("anliza"+springDay+"f"+springMonth+"d"+lunarMonth+isLunarRain);
	}
	/**
	 * @author cloud
	 *判断是够是闰年
	 */
	private boolean isRain(int year) {
			
		if(year%4==0&&(year%100!=0))
			{
				return true;
			}else if(year%400==0)return true;
		return false;
		
		
	}

	private void initview() {
			textView4=(TextView)findViewById(R.id.textView4);
		    textView5=(TextView)findViewById(R.id.textView5);
		    textView6=(TextView)findViewById(R.id.textView6);
		    textView7=(TextView)findViewById(R.id.textView7);
		    textView8=(TextView)findViewById(R.id.textView8);
		    textView9=(TextView)findViewById(R.id.textView9);
		    editText1=(EditText) findViewById(R.id.editText1);
		    editText2=(EditText) findViewById(R.id.editText2);
		    editText3=(EditText) findViewById(R.id.editText3);
		    mButton=(Button)findViewById(R.id.button1);
			
	}
}
