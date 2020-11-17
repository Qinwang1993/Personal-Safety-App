package com.sjsu.sister.util;

import com.sjsu.sister.model.FirstAids;
import com.sjsu.sister.model.HealthCard;

import java.util.ArrayList;
import java.util.List;

public class FirstAidUtils {

    public static FirstAids getFirstAid(int id){
        List<FirstAids> firstAidsList = new ArrayList<>();
        firstAidsList.add(new FirstAids("Basic First Aid for Cardiac Arrest",
                "https://www.verywellhealth.com/thmb/CpgU2ANLfkr4FoMNGaMrq1vF4Sc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/how-to-do-cpr-1298446-4a04444fabe0467aa9194a9161e5cdb2.png",
                "First Aid for Suspected Cardiac Arrest",
                "Cardiopulmonary resuscitation (CPR) is the most important medical procedure of all. If a person is in cardiac arrest (the heart is no longer pumping blood) and CPR is not performed, that person will die. On the other hand, performing CPR or using an automated external defibrillator (AED) could save a life.2\uFEFF\n" +
                        "\n" +
                        "You can start by reviewing the basics of CPR. The procedure has changed in the past few years, so it is best to take a CPR class at a medical center, community college, Red Cross, or fire department. There is no substitute for a hands-on class.\n" +
                        "\n" +
                        "AEDs are available in many public areas and businesses. These devices are simplified for use even if you have never been trained. CPR training will include familiarization with AED use.",
                "According to the American Heart Association and American Red Cross 2019 guidelines, the steps to take when a cardiac arrest is suspected are:\n" +
                        "\n" +
                        "\t\t• Command someone to call 911 or the medical alert system for the locale.\n" +
                        "\t\t• Immediately start chest compressions regardless of your training. Compress hard and fast in the center of the chest, allowing recoil between compressions. Hand this task over to those who are trained if and when they arrive.\n" +
                        "\t\t• If you are trained, use chest compressions and rescue breathing.\n" +
                        "\t\t• An AED should be applied and used. But it is essential not to delay chest compressions, so finding one should be commanded to someone else while you are doing chest compressions.",
                null));


        firstAidsList.add(new FirstAids("Basic First Aid for Bleeding",
                "https://www.verywellhealth.com/thmb/_Qe5EmFvRXsD8ASz3ED9Rbic8PQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/GettyImages-525386797-5a6a1eccfa6bcc003690b97f.jpg",
                "First Aid for Bleeding",
                "Regardless of how severe, almost all bleeding can be controlled. Mild bleeding will usually stop on its own. If severe bleeding is not controlled, it may lead to shock and eventually death.",
                "Steps to take if you are faced with bleeding right now:\n" +
                        "\n" +
                        "\t\t1.Cover the wound with a gauze or a cloth.\n" +
                        "\t\t2.Apply direct pressure to stop the blood flow.\n" +
                        "\t\t3.Don't remove the cloth. Add more layers if needed. The cloth will help clots form to stop the flow.",
                "In most cases, applying a tourniquet may do more damage to the limb than good. The 2010 American Heart Association guidelines also discount the value of elevation and using pressure points."));

        firstAidsList.add(new FirstAids("Basic First Aid for Burns",
                "https://www.verywellhealth.com/thmb/nTMbI1OIHAEqOUxa_dMgSD7-kxc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/how-to-treat-a-burn-1298802-01-ef3683609e664a3ea3c46d6a619ac2f0.png",
                "First Aid for Burns",
                "The first step to treating a burn is to stop the burning process.Chemicals need to be cleaned off. Electricity needs to be turned off. Heat needs to be cooled down with running water. Sunburn victims need to be covered up or go inside.\n" +
                        "\n" +
                        "No matter what caused the burns or how bad they are, stopping the burn comes before treating the burn. The severity of a burn is based on depth and size. For serious burns, you might need to see a doctor or call 911.",
                "Take these first aid steps:\n" +
                        "\n" +
                        "\t\t1.Flush the burned area with cool running water for several minutes. Do not use ice.\n" +
                        "\t\t2.Apply a light gauze bandage.\n" +
                        "\t\t3.Do not apply ointments, butter, or oily remedies to the burn.\n" +
                        "\t\t4.Take ibuprofen or acetaminophen for pain relief if necessary.\n" +
                        "\t\t5.Do not break any blisters that may have formed.",
                null));

        firstAidsList.add(new FirstAids("Basic First Aid for Bee Stings",
                "https://www.verywellhealth.com/thmb/Gi6_ASqY6GZxkNoSAx4bXXTxbps=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/how-to-treat-a-bee-sting-1298219-01-b223cd5a55f047599c3facbe5d135f19.png",
                "How to Treat a Bee Sting Safely?",
                "Bee stings are always at least painful and they can be deadly if the patient is allergic to bee venom. If a bee sting patient has had any allergic reactions to bee stings in the past, they have a higher chance of showing signs of possible anaphylaxis, a life-threatening allergic reaction.",
                "\t\t1.Take out the Stinger\n" +
                        "\t\t2.Treat Local Reactions\n" +
                        "\t\t3.Recognize an Emergency\n" +
                        "\t\t\tSigns and symptoms of severe allergy or anaphylaxis include:\n" +
                        "\t\t\t\t○ Itching in places other than the sting site\n" +
                        "\t\t\t\t○ Redness other than at the site\n" +
                        "\t\t\t\t○ Hives (raised welts) which can develop all over the body\n" +
                        "\t\t\t\t○ Shortness of breath\n" +
                        "\t\t\tA person exhibiting these signs needs emergency medical care.\n",
                "If there is any concern that the patient may be developing anaphylaxis, call 911 immediately. Antihistamines, such as diphenhydramine (Benadryl), can slow an anaphylactic reaction, but will not reverse it. If they don't get treated by medical professionals quickly, anaphylaxis patients can die from the reaction."));

        firstAidsList.add(new FirstAids("Basic First Aid for Frostbite",
                "https://www.verywellhealth.com/thmb/46f4bZQfv4ucf9SkFz89aLRVue8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/mature-man-outdoors-in-nature-on-a-cold-winter-day-1049557336-46dbf36f4dc342c0a8b2486345b8219f.jpg",
                "How to Treat Frostbite",
                "Frostbite occurs when skin and underlying tissue freeze from exposure to extremely cold temperatures. While it's mild form, frostnip, causes redness and numbness that can be self-treated with proper first aid, more advanced stages of frostbite require emergency medical attention. Frostbite treatment includes controlled rewarming, and potential interventions such as IV fluids and medications.",
                "To start providing first-aid treatment:\n" +
                        "\n" +
                        "\t\t1.Immerse the affected body part in warm water (between 98 and 105 degrees; normal body temperature or a little warmer). If you don’t have a thermometer, feel the water with an uninjured hand to make sure it’s comfortable and won’t cause burns.\n" +
                        "\t\t2.Soak the frozen area for 30 minutes. Continue to refresh the water in the container as it cools to keep it at a consistent temperature. If you don’t have access to water, wrap the area gently with clothes or a blanket to help get warm.\n" +
                        "\t\t3.Depending on the amount of damage, warming the skin can be very painful as the numbness fades. If available, you can give an over-the-counter non-steroidal anti-inflammatory drug like ibuprofen to help with symptoms until you can get to the hospital. \n" +
                        "\t\t4.During the warming process, the skin may start to blister. To avoid infection, do not rupture any of the blisters. You can apply bulky sterile dressing to the area once dried. Make sure that the bandages are loose, not tight.",
                "Never rub or massage frostbitten tissue. Rubbing frostbitten tissue will result in more severe damage. Don’t use any heating devices, stoves, or fires to treat frostbite. Patients cannot feel the frostbitten tissue and can be burned easily."));

        firstAidsList.add(new FirstAids("Basic First Aid for a Puncture Wound",
                "https://www.verywellhealth.com/thmb/dTsaxZnmh90emIL0pQX-DAGgL6Y=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/bandaging-finger-92245459-aaf091ea0fb1485fb5af6e87e89563b9.jpg",
                "First Aid Treatment for a Puncture Wound",
                "How do you best treat a puncture wound and how do these differ from lacerations and other types of injuries? What do you need to be aware of and watch for if you suffer one of these injuries?",
                "If you encounter a person with a puncture wound the first step is to protect yourself.\n" +
                        "Once you determine that you are safe to be near the victim, and after you have protected yourself with gloves and eyewear protection if indicated, follow these steps.\n"+
                        "\t\t1.Control bleeding before anything else. Putting pressure directly on the puncture wound while holding it at a level above the heart (if possible) for 15 minutes should be enough to stop bleeding. If not, try using pressure points. Pressure points are areas where blood vessels lie close to the surface of the skin and include the brachial artery (between the shoulder and elbow), the femoral artery (in the groin along the bikini line), and the popliteal artery (behind the knee). Tourniquets should be avoided unless medical care will be delayed for several hours.\n" +
                        "\t\t2.Know when to call 911. Call 911 right away for puncture wounds of any depth in the neck or if a deep puncture wound (or one of unknown depth) occurs to the abdomen, back, pelvis, thigh, or chest. Puncture wounds in other regions, even if shallow, should prompt you to call 911 if the bleeding will not stop. Holes in the chest can lead to collapsed lungs. Deep puncture wounds to the chest should be immediately sealed by hand or with a dressing that does not allow air to flow. Victims may complain of shortness of breath. If the victim gets worse after sealing the chest puncture wound, unseal it.\n" +
                        "\t\t3.When bleeding is controlled, wash the wound. Once bleeding has been controlled, wash the puncture wound with warm water and mild soap (see illustration). If bleeding starts again, repeat step two.\n" +
                        "\t\t4.Determine if the wound needs stitches. Wide puncture wounds may need stitches. If the victim needs stitches, proceed to the emergency department\n" +
                        "\t\t5.Properly dress the wound. For smaller puncture wounds that do not require stitches, use antiseptic ointment and cover with adhesive bandages.\n" +
                        "\t\t6.Watch for signs of infection. When you change the bandages, or if the victim develops a fever, chills, or is feeling poorly, check for signs of infection. Increased redness, swelling, or drainage, especially pus-like drainage is a sign that you should contact a doctor. If redness begins to radiate or streak away from the puncture wound, contact your doctor right away.\n" +
                        "\t\t7.Clean and change bandages daily. Clean and change the dressings (bandages) over a puncture wound daily. Each time you change the dressing you should clean the wound and look for signs of infection.\n" +
                        "\t\t8.Give pain relief if needed. Use acetaminophen or ibuprofen for pain relief as needed as long as there are no reasons why these should not be used (such as kidney disease).",
                null));

        firstAidsList.add(new FirstAids("Basic First Aid for Choking",
                "https://www.verywellhealth.com/thmb/eez_1vIVOEa9Qr3UxA4xJdHH7F4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/GettyImages-136811278-664fedac86fc431d84ab156fdfeda4e4.jpg",
                "What Should I Do If Someone Is Choking?",
                "Choking happens when something—food or another item—is caught in the back of the throat. If the object (or food) blocks the top of the trachea a person may be unable to breathe. This is an emergency. It is also possible that food or other things can get stuck in the esophagus; while painful, this does not cause a person to stop breathing. This article will cover causes, prevention and the treatment of choking.",
                "If someone is choking, you should determine whether or not they can talk. If they can talk, cough or make other noises that indicate air passage, let them clear their airway on their own. Intervention at this point may cause further lodging of the object to occur.\n" +
                        "\n" +
                        "If an individual has something caught in the esophagus they will still be able to speak and breathe but it may be painful, especially when swallowing. They may also drool. You should seek medical attention so the object can either be retrieved or pushed into the stomach/intestines using a scope (EGD).\n" +
                        "\n" +
                        "If the person choking is not able to speak or make other noises, they will not be able to breathe either. An indication that a person is not breathing is cyanosis. This is an emergency. You should start abdominal thrusts, also known as the Heimlich maneuver. If the person at any point becomes unresponsive (unconscious), you should begin CPR. If you are not alone, have someone else call 9-1-1. If you are alone call 911 immediately and (if possible) stay on the line while performing CPR.\n",
                "Prevention is key when it comes to choking. Educating yourself on common causes of choking can help prevent complications from occurring and keep your loved ones safe."));
        return firstAidsList.get(id);
    }

}
