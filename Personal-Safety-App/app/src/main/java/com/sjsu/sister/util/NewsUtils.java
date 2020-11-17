package com.sjsu.sister.util;

import com.sjsu.sister.model.HealthCard;
import com.sjsu.sister.model.News;
import com.sjsu.sister.model.Videos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsUtils {

    public static List<News> getNewsList() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News("COVID-19 Severity",
                "https://i.ibb.co/nBhtv34/Xnip2020-11-15-01-16-54.jpg",
                "Most people infected with COVID-19 will only have mild symptoms and fully recover. Yet, some people are more at risk.",
                "We all have a role to play in protecting ourselves and others. Know the facts about COVID-19 and help stop the spread of rumours and the disease.",
                0));

        newsList.add(new News("COVID-19 Transmission and Protective measures",
                "https://i.ibb.co/DY2KdK9/Xnip2020-11-14-16-29-16.jpg",
                "Protect yourself and others by making these 6 simple precautions your new habits:",
                "\t\t• Clean your hands often\n" +
                        "\t\t• Cough or sneeze in your bent elbow - not your hands!\n" +
                        "\t\t• Avoid touching your eyes, nose and mouth\n" +
                        "\t\t• Limit social gatherings and time spent in crowded places",
                1));



        newsList.add(new News("COVID-19 and Food safety",
                "https://i.ibb.co/G0wVwpZ/Xnip2020-11-15-00-09-37.jpg",
                "Advice on food safety and healthy eating during the COVID-19 outbreak.",
                null,
                2));

        newsList.add(new News("COVID-19: Physical distancing",
                "https://i.ibb.co/b2bzNHM/Xnip2020-11-15-00-12-03.jpg",
                "Physical distancing helps limit the spread of COVID-19 – this means we keep a distance of at least 1m from each other and avoid spending time in crowded places or in groups.",
                "\t\t• Protect yourself and others.\n"+
                        "\t\t• Break the chain of transmission.\n",
                3));

        newsList.add(new News("Recognize and Respond to COVID-19",
                "https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide2069e18f870374cec818c01cae6a57c13.jpg?sfvrsn=77a95407_2",
                "Do you have symptoms of coronavirus? Not sure what to do? Always follow your national health authority’s advice.",
                "\t\tSymptoms of COVID-19 can vary, but mild cases often experience fever, cough, and fatigue. Moderate cases may have difficulty breathing or mild pneumonia. While severe cases may have severe pneumonia, other organ failure & possible death." +
                        "\n" +
                        "\t\tAnyone experiencing difficulty breathing should seek immediate medical attention.",
                4));




        return newsList;
    }
}
