package com.n26.statistics.state;


public class StatisticsMachineState {
   private StatisticsState statisticsState;

   public StatisticsMachineState(StatisticsState statisticsState) {
       this.statisticsState = statisticsState;
   }

   public void setState(StatisticsState statisticsState){
       this.statisticsState = statisticsState;
   }

   public StatisticsState getState(){
       return statisticsState;
   }
}
