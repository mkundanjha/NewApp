package com.galanto.GalantoHealth;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.newapp.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsPage extends Fragment {
    View view;
    AnyChartView barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_details_page, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart=view.findViewById(R.id.barchart);

        Cartesian cartesian= AnyChart.column();

        List<DataEntry> data=new ArrayList<>();
        data.add(new ValueDataEntry("W",0));
        data.add(new ValueDataEntry("T",26));
        data.add(new ValueDataEntry("I",23));
        data.add(new ValueDataEntry("M",78));
        data.add(new ValueDataEntry("R",55));
        data.add(new ValueDataEntry("P",34));

        Column column=cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

//        cartesian.animation(true);

//        cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.yScale().minimum(0d);


        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

//        cartesian.xAxis(0).title("Product");
//        cartesian.yAxis(0).title("Revenue");
        barChart.setBackgroundColor(Color.RED);
        barChart.setChart(cartesian);



    }

}