package com.sjsu.sister.util;

import com.sjsu.sister.model.HealthCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HealthCardUtils {
    public static List<HealthCard> getHealthCards(int id){
        List<List<HealthCard>> list = new ArrayList<>();

        list.add(new ArrayList<HealthCard>());
        list.get(0).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/severity-slide2.jpg?sfvrsn=6e7ead81_6",
                "While COVID-19 is spreading rapidly, most people will experience only mild or moderate symptoms.",
                "That said, this coronavirus can cause severe disease in some people."));
        list.get(0).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/severity-slide3.png?sfvrsn=eae6f0fd_2",
                "The vast majority of people who have contracted the new coronavirus to date have recovered or are recovering.",
                "However, even a disease that 95% of people are recovering from can cause a significant number of deaths if it infects enough people."));
        list.get(0).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/severity-slide4.png?sfvrsn=a6fd06c9_2",
                "There have been relatively few COVID-19 infections among children.",
                "Older age groups, especially those with underlying health conditions, are more at risk."));
        list.get(0).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/severity-slide5.png?sfvrsn=7fbef143_2",
                "We’re particularly worried about the impact of COVID-19 on the most vulnerable:",
                "Health workers, people over 60 and those with underlying health conditions. These groups need our protection."));
        list.get(0).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/severity-slide6.png?sfvrsn=dd9a1e53_2",
                "Protect yourself and others from COVID-19.",
                "Follow these simple precautions to keep yourself and others safe from COVID-19."));


        list.add(new ArrayList<HealthCard>());
        list.get(1).add(new HealthCard("https://www.who.int/images/default-source/wpro/infographics/coronavirus-(covid-19)/transmission-1-e.png?sfvrsn=340c587_0",
                "COVID-19 spreads primarily from person to person",
                "This can be via droplets released when people sick with the new coronavirus cough or sneeze. It can also spread when you’re in close contact with someone who’s sick – e.g. when you hug or shake hands."));

        list.get(1).add(new HealthCard("https://www.who.int/images/default-source/wpro/infographics/coronavirus-(covid-19)/transmission-slide7.png?sfvrsn=f5dbe58a_0",
                "COVID-19 spreads primarily through close contact with someone who is infected, but it can also spread if you touch contaminated objects and surfaces",
                "You can protect yourself: clean your hands and your surroundings frequently!"));
        list.get(1).add(new HealthCard("https://www.who.int/images/default-source/wpro/infographics/coronavirus-(covid-19)/transmission-slide11.png?sfvrsn=93eea205_0",
                "How can you recognize the symptoms of COVID-19?",
                "Protect yourself and others: make these 5 simple precautions your new habits."));


        list.add(new ArrayList<HealthCard>());
        list.get(2).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide28700f73e1e3e4387b8a0b934d03d81e4.jpg?sfvrsn=203eb45d_2",
                "COVID-19 appears to spread primarily from person to person, rather than via food.",
                "But it is always a good idea to prepare nutritious food for ourselves and our families, and to learn how to do so safely."));

        list.get(2).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide3ea2471cde821486db08ee0137a9c5c19.jpg?sfvrsn=e20539b9_2",
                "Food safety is as simple as these 5 keys!",
                null));

        list.get(2).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide432fd3029697544789dbaab4e46ac46fa.jpg?sfvrsn=f08874ab_2",
                "Eating healthy during COVID-19 is important.",
                "Plan your groceries in advance to maintain a healthy diet and avoid food waste."));

        list.get(2).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide5e1fe98347f134b6b83af38f1736fb773.jpg?sfvrsn=a6edd32c_2",
                "Remember these key tips while at the supermarket to keep yourself safe and to make healthy food choices.",
                null));

        list.get(2).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide7cd8ac7d51bb8409698ee4fa0c8b8c2e1.jpg?sfvrsn=993287a2_2",
                "After returning from grocery shopping, take these precautions to stay safe from COVID-19 and many other diseases.",
                null));


        list.add(new ArrayList<HealthCard>());
        list.get(3).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide68ab9130be83c443789a08d7b2a4f93af.tmb-549v.jpg?sfvrsn=ef855793_1",
                "Be a hero and break the chain of COVID-19 transmission by practicing physical distancing.",
                "This means we keep a distance of at least 1m from each other and avoid spending time in crowded places or in groups."));

        list.get(3).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide3479d167dee744db38540eaa8e56c32f7.tmb-549v.jpg?sfvrsn=43e95b39_1",
                "We do not always know who may be sick with coronavirus.",
                "That’s why it’s important to stay home and practice physical distancing. If we must go out, e.g. to buy groceries or for medical reasons, we should stay at least 1m away from others."));

        list.get(3).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide40b0be645e6ee4038b1ea48ff3813dc74.jpg?sfvrsn=a5e5a609_2",
                "Staying at home as much as possible is the right thing to do now.",
                "Limiting gatherings with people outside your household – like group activities and religious events – can reduce the spread of COVID-19.\n" +
                        "Protect yourself and others. Do your part. Stay home."));

        list.get(3).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide10c5b30104746844cba14fa735bcf42cbc.tmb-479v.jpg?sfvrsn=16db4573_1",
                "While you may need to leave your home while physical distancing, follow national health advice and stay safe.",
                "Together, we can break the chain of COVID-19 transmission."));

        list.add(new ArrayList<HealthCard>());
        list.get(4).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide2069e18f870374cec818c01cae6a57c13.jpg?sfvrsn=77a95407_2",
                "How can you recognize the symptoms of COVID-19?",
                "Symptoms vary, but mild cases often experience fever, cough, and fatigue. Moderate cases may have difficulty breathing or mild pneumonia. While severe cases have severe pneumonia, other organ failure & possible death."));

        list.get(4).add(new HealthCard("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide3c8652f2f6f694fd0a28637c1e759d306.jpg?sfvrsn=900c60f9_2",
                "Follow advice from your national health authority on what to do if you have COVID-19 symptoms.",
                "In some situations, people who are at low risk may be asked to stay home, self-isolate and rest."));

        return list.get(id);
    }

}
