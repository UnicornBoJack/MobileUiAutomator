package com.jens.automation2;

import java.sql.Time;
import java.util.ArrayList;

public class TimeFrame
{
	// Defines a timeframe
	protected Time triggerTimeStart;
	protected Time triggerTimeStop;
	protected long repetition;

	protected final static String separator = "/";

	private ArrayList<Integer> dayList = new ArrayList<Integer>();
	public ArrayList<Integer> getDayList()
	{
		return dayList;
	}
	public void setDayList(ArrayList<Integer> dayList)
	{
		this.dayList = dayList;
	}
	public void setDayListFromString(String dayListString)
	{
//		Log.i("Parsing", "Full string: " + dayListString);
		char[] dayListCharArray = dayListString.toCharArray();

		dayList = new ArrayList<Integer>();
		for(char item : dayListCharArray)
		{
//				Log.i("Parsing", String.valueOf(item));
			dayList.add(Integer.parseInt(String.valueOf(item)));
		}
	}
		
	public Time getTriggerTimeStart()
	{
		return triggerTimeStart;
	}
	public void setTriggerTimeStart(Time triggerTimeStart)
	{
		this.triggerTimeStart = triggerTimeStart;
	}

	public Time getTriggerTimeStop()
	{
		return triggerTimeStop;
	}
	public void setTriggerTimeStop(Time triggerTimeStop)
	{
		this.triggerTimeStop = triggerTimeStop;
	}

	public long getRepetition()
	{
		return repetition;
	}

	public void setRepetition(long repetition)
	{
		this.repetition = repetition;
	}

	public TimeFrame (Time timeStart, Time timeEnd, ArrayList<Integer> dayList2, long repetition)
	{
		this.setTriggerTimeStart(timeStart);
		this.setTriggerTimeStop(timeEnd);
		this.setDayList(dayList2);
		this.setRepetition(repetition);
	}

	public TimeFrame (String fileContent)
	{
		String[] dateArray = fileContent.split(separator); // example: timestart/timestop/days[int]/repetition
		this.setTriggerTimeStart(Time.valueOf(dateArray[0]));
		this.setTriggerTimeStop(Time.valueOf(dateArray[1]));
		this.setDayListFromString(dateArray[2]);
		if(dateArray.length > 3)	// may not exist in old config files
			this.setRepetition(Long.parseLong(dateArray[3]));
	}

	public String toTriggerParameter2String()
	{
		StringBuilder response = new StringBuilder();
		response.append(this.getTriggerTimeStart().getHours() + ":" + this.getTriggerTimeStart().getMinutes() + ":0");
		response.append(separator);
		response.append(this.getTriggerTimeStop().getHours() + ":" + this.getTriggerTimeStop().getMinutes() + ":0");
		response.append(separator);

		StringBuilder days = new StringBuilder();

		for(int day : dayList)
			days.append(String.valueOf(day));

		response.append(days.toString());

		if(this.repetition > 0)
		{
			response.append(separator + this.getRepetition());
		}

		return response.toString();
	}

	@Override
	public String toString()
	{
		String returnString = this.getTriggerTimeStart().toString() + separator + this.getTriggerTimeStop().toString() + separator;

		for(Integer oneDay : this.getDayList())
			returnString += String.valueOf(oneDay);

		returnString += separator + String.valueOf(repetition);

		return returnString;
	}
}