package io.uscool.fuelfriend.Data;

import android.content.Context;
import android.widget.Filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.uscool.fuelfriend.model.TownSuggestion;
import io.uscool.fuelfriend.model.TownWrapper;

/**
 * Created by ujjawal on 1/7/17.
 */

public class TownDataHelper {
    private static final String TOWN_FILE_NAME = "town.json";

    private static List<TownWrapper> mTownWrappers = new ArrayList<>();

    private static List<TownSuggestion> mTownSuggestions =
            new ArrayList<>(Arrays.asList(
                    new TownSuggestion("Delhi"),new TownSuggestion("Mumbai"),new TownSuggestion("Kolkata"),new TownSuggestion("Chennai"),new TownSuggestion("Machilipatnam"),new TownSuggestion("Kurnool"),new TownSuggestion("Eluru"),new TownSuggestion("Guntur"),new TownSuggestion("Vizianagaram"),new TownSuggestion("Anantapur"),new TownSuggestion("Kadapa"),new TownSuggestion("Vizag"),new TownSuggestion("Ongole"),new TownSuggestion("Nellore"),new TownSuggestion("Chittoor"),new TownSuggestion("Kakinada"),new TownSuggestion("Srikakulam"),new TownSuggestion("Nalbari"),new TownSuggestion("Nagaon"),new TownSuggestion("Silchar"),new TownSuggestion("Dibrugarh"),new TownSuggestion("Jorhat"),new TownSuggestion("Barpeta"),new TownSuggestion("Golaghat"),new TownSuggestion("GUWAHATI"),new TownSuggestion("Tezpur"),new TownSuggestion("Sibsagar"),new TownSuggestion("Purnia"),new TownSuggestion("Motihari"),new TownSuggestion("Lakhisarai"),new TownSuggestion("Jamui"),new TownSuggestion("Siwan"),new TownSuggestion("Muzaffarpur"),new TownSuggestion("Madhubani"),new TownSuggestion("Banka"),new TownSuggestion("Hajipur"),new TownSuggestion("Supaul"),new TownSuggestion("Patna"),new TownSuggestion("Nawada"),new TownSuggestion("Buxar"),new TownSuggestion("Bettiah"),new TownSuggestion("Sheikhpura"),new TownSuggestion("Madhepura"),new TownSuggestion("Chhapra"),new TownSuggestion("Bihar Sharif"),new TownSuggestion("Kishanganj"),new TownSuggestion("Sasaram"),new TownSuggestion("Munger"),new TownSuggestion("Bhagalpur"),new TownSuggestion("Araria"),new TownSuggestion("Gaya"),new TownSuggestion("Aurangabad"),new TownSuggestion("Samastipur"),new TownSuggestion("Darbhanga"),new TownSuggestion("Begusarai"),new TownSuggestion("Arrah"),new TownSuggestion("Khagaria"),new TownSuggestion("Katihar"),new TownSuggestion("Jehanabad"),new TownSuggestion("Sitamarhi"),new TownSuggestion("Sheohar"),new TownSuggestion("Chandigarh"),new TownSuggestion("Raigarh"),new TownSuggestion("Durg"),new TownSuggestion("Bilaspur"),new TownSuggestion("Narayanpur"),new TownSuggestion("Korba"),new TownSuggestion("Mahasamund"),new TownSuggestion("Jagdalpur"),new TownSuggestion("Surajpur"),new TownSuggestion("Dhamtari"),new TownSuggestion("Kanker"),new TownSuggestion("Rajnandgaon"),new TownSuggestion("Raipur"),new TownSuggestion("Naila Janjgir"),new TownSuggestion("Dantewada"),new TownSuggestion("Ambikapur"),new TownSuggestion("Jashpur"),new TownSuggestion("Kawardha"),new TownSuggestion("Silvassa"),new TownSuggestion("Daman"),new TownSuggestion("Panaji"),new TownSuggestion("Margao"),new TownSuggestion("Kheda"),new TownSuggestion("Himmatnagar"),new TownSuggestion("Vadodara"),new TownSuggestion("Surendranagar"),new TownSuggestion("Anand"),new TownSuggestion("Godhra"),new TownSuggestion("Mehsana"),new TownSuggestion("Amreli"),new TownSuggestion("Gandhinagar"),new TownSuggestion("Palanpur"),new TownSuggestion("Dahod"),new TownSuggestion("Surat"),new TownSuggestion("Porbandar"),new TownSuggestion("Bhuj"),new TownSuggestion("Bhavnagar"),new TownSuggestion("Bharuch"),new TownSuggestion("Jamnagar"),new TownSuggestion("Patan"),new TownSuggestion("Rajkot"),new TownSuggestion("Navsari"),new TownSuggestion("Ahmedabad"),new TownSuggestion("Junagadh"),new TownSuggestion("Panchkula"),new TownSuggestion("Panipat"),new TownSuggestion("Nuh"),new TownSuggestion("Rohtak"),new TownSuggestion("Palwal"),new TownSuggestion("Bhiwani"),new TownSuggestion("Karnal"),new TownSuggestion("Kaithal"),new TownSuggestion("Jind"),new TownSuggestion("Jhajjar"),new TownSuggestion("Faridabad"),new TownSuggestion("Ambala"),new TownSuggestion("Hissar"),new TownSuggestion("Rewari"),new TownSuggestion("Gurgaon"),new TownSuggestion("Yamuna Nagar"),new TownSuggestion("Kurukshetra"),new TownSuggestion("Sirsa"),new TownSuggestion("Fatehabad"),new TownSuggestion("Sonepat"),new TownSuggestion("Chamba"),new TownSuggestion("Mandi"),new TownSuggestion("Solan"),new TownSuggestion("Kullu"),new TownSuggestion("Bilaspur"),new TownSuggestion("Una"),new TownSuggestion("Shimla"),new TownSuggestion("Nahan"),new TownSuggestion("Hamirpur"),new TownSuggestion("Shopian"),new TownSuggestion("Rajauri"),new TownSuggestion("Badgam"),new TownSuggestion("Kishtwar"),new TownSuggestion("Kupwara"),new TownSuggestion("Kathua"),new TownSuggestion("Leh"),new TownSuggestion("Srinagar"),new TownSuggestion("Samba"),new TownSuggestion("Poonch"),new TownSuggestion("Doda"),new TownSuggestion("Anantnag"),new TownSuggestion("Jammu"),new TownSuggestion("Kargil"),new TownSuggestion("Udhampur"),new TownSuggestion("Daltonganj"),new TownSuggestion("Godda"),new TownSuggestion("Hazaribag"),new TownSuggestion("Dumka"),new TownSuggestion("Dhanbad"),new TownSuggestion("Giridih"),new TownSuggestion("Deoghar"),new TownSuggestion("Bokaro"),new TownSuggestion("Ramgarh"),new TownSuggestion("Koderma"),new TownSuggestion("Pakur"),new TownSuggestion("Chaibasa"),new TownSuggestion("RANCHI-FDZ"),new TownSuggestion("Jamshedpur"),new TownSuggestion("Gumla"),new TownSuggestion("Seraikela"),new TownSuggestion("Lohardaga"),new TownSuggestion("Garhwa"),new TownSuggestion("Ranchi"),new TownSuggestion("Dharwad"),new TownSuggestion("Davanagere"),new TownSuggestion("Chamarajanagar"),new TownSuggestion("Bijapur"),new TownSuggestion("Koppal"),new TownSuggestion("Bengaluru"),new TownSuggestion("Bellary"),new TownSuggestion("Bagalkot"),new TownSuggestion("Karwar"),new TownSuggestion("Haveri"),new TownSuggestion("Raichur"),new TownSuggestion("Mangalore"),new TownSuggestion("Mandya"),new TownSuggestion("Chikballapur"),new TownSuggestion("Bidar"),new TownSuggestion("Belgaum"),new TownSuggestion("Tumkur"),new TownSuggestion("Chitradurga"),new TownSuggestion("Hassan"),new TownSuggestion("Gadag"),new TownSuggestion("Udupi"),new TownSuggestion("Kolar"),new TownSuggestion("Ramanagara"),new TownSuggestion("Shimoga"),new TownSuggestion("Mysore"),new TownSuggestion("Gulbarga"),new TownSuggestion("Yadgir"),new TownSuggestion("Chikmagalur"),new TownSuggestion("Thiruvananthapuram"),new TownSuggestion("Pathanamthitta"),new TownSuggestion("Painavu"),new TownSuggestion("Kannur"),new TownSuggestion("Palakkad"),new TownSuggestion("Kollam"),new TownSuggestion("Kasaragod"),new TownSuggestion("Kozhikode"),new TownSuggestion("Kakkanad"),new TownSuggestion("Thrissur"),new TownSuggestion("Kottayam"),new TownSuggestion("Malappuram"),new TownSuggestion("Kalpetta"),new TownSuggestion("Alappuzha"),new TownSuggestion("Narsinghpur"),new TownSuggestion("Khargone"),new TownSuggestion("Jhabua"),new TownSuggestion("Jabalpur"),new TownSuggestion("Vidisha"),new TownSuggestion("Rewa"),new TownSuggestion("Bhind"),new TownSuggestion("Ashok Nagar"),new TownSuggestion("Alirajpur"),new TownSuggestion("Gwalior"),new TownSuggestion("Tikamgarh"),new TownSuggestion("Singrauli"),new TownSuggestion("Sagar"),new TownSuggestion("Ratlam"),new TownSuggestion("Neemuch"),new TownSuggestion("Dewas"),new TownSuggestion("Balaghat"),new TownSuggestion("Anuppur"),new TownSuggestion("Guna"),new TownSuggestion("Umaria"),new TownSuggestion("Seoni"),new TownSuggestion("Sehore"),new TownSuggestion("Raisen"),new TownSuggestion("Panna"),new TownSuggestion("Mandsaur"),new TownSuggestion("Chhatarpur"),new TownSuggestion("Bhopal"),new TownSuggestion("Betul"),new TownSuggestion("Datia"),new TownSuggestion("Barwani"),new TownSuggestion("Katni"),new TownSuggestion("Sidhi"),new TownSuggestion("Shahdol"),new TownSuggestion("Rajgarh"),new TownSuggestion("Dindori"),new TownSuggestion("Burhanpur"),new TownSuggestion("Ujjain"),new TownSuggestion("Satna"),new TownSuggestion("Morena"),new TownSuggestion("Chhindwara"),new TownSuggestion("Khandwa"),new TownSuggestion("Indore"),new TownSuggestion("Hoshangabad"),new TownSuggestion("Harda"),new TownSuggestion("Shivpuri"),new TownSuggestion("Shajapur"),new TownSuggestion("Mandla"),new TownSuggestion("Dhar"),new TownSuggestion("Damoh"),new TownSuggestion("Sheopur"),new TownSuggestion("Nandurbar"),new TownSuggestion("Latur"),new TownSuggestion("Chandrapur"),new TownSuggestion("Amravati"),new TownSuggestion("Kolhapur"),new TownSuggestion("Thane"),new TownSuggestion("Bhandara"),new TownSuggestion("Jalna"),new TownSuggestion("Wardha"),new TownSuggestion("Nanded"),new TownSuggestion("Nagpur"),new TownSuggestion("Pune"),new TownSuggestion("Osmanabad"),new TownSuggestion("Alibag"),new TownSuggestion("Akola"),new TownSuggestion("Jalgaon"),new TownSuggestion("Sangli"),new TownSuggestion("Ratnagiri"),new TownSuggestion("Gadchiroli"),new TownSuggestion("Nashik"),new TownSuggestion("Buldhana"),new TownSuggestion("Ahmednagar"),new TownSuggestion("Gondia"),new TownSuggestion("Satara"),new TownSuggestion("Dhule"),new TownSuggestion("Beed"),new TownSuggestion("Hingoli"),new TownSuggestion("Washim"),new TownSuggestion("Parbhani"),new TownSuggestion("Aurangabad"),new TownSuggestion("Solapur"),new TownSuggestion("Imphal"),new TownSuggestion("Nongstoin"),new TownSuggestion("Tura"),new TownSuggestion("Jowai"),new TownSuggestion("Shillong"),new TownSuggestion("Aizawl"),new TownSuggestion("Kohima"),new TownSuggestion("Dimapur"),new TownSuggestion("Shahdara"),new TownSuggestion("Saket"),new TownSuggestion("Rajouri Garden"),new TownSuggestion("Sadar Bazar"),new TownSuggestion("Vasant Vihar"),new TownSuggestion("Preet Vihar"),new TownSuggestion("Kanjhawala"),new TownSuggestion("Delhi"),new TownSuggestion("Cuttack"),new TownSuggestion("Koraput"),new TownSuggestion("Gajapati"),new TownSuggestion("Sundargarh"),new TownSuggestion("Sambalpur"),new TownSuggestion("Puri"),new TownSuggestion("Nayagarh"),new TownSuggestion("Bhadrak"),new TownSuggestion("Baripada"),new TownSuggestion("Dhenkanal"),new TownSuggestion("Bargarh"),new TownSuggestion("Jharsuguda"),new TownSuggestion("Malkangiri"),new TownSuggestion("Bhubaneswar"),new TownSuggestion("Rayagada"),new TownSuggestion("Balangir"),new TownSuggestion("Angul"),new TownSuggestion("Phulbani"),new TownSuggestion("Bhawanipatna"),new TownSuggestion("Balasore"),new TownSuggestion("Kendujhar"),new TownSuggestion("Pandakkal"),new TownSuggestion("Pondicherry"),new TownSuggestion("Karaikal"),new TownSuggestion("Yanam"),new TownSuggestion("Mansa"),new TownSuggestion("Amritsar"),new TownSuggestion("Sri Muktsar Sahib"),new TownSuggestion("Sangrur"),new TownSuggestion("Ludhiana"),new TownSuggestion("Gurdaspur"),new TownSuggestion("Fazilka"),new TownSuggestion("Pathankot"),new TownSuggestion("Moga"),new TownSuggestion("Hoshiarpur"),new TownSuggestion("Firozpur"),new TownSuggestion("Mohali"),new TownSuggestion("Bathinda"),new TownSuggestion("Patiala"),new TownSuggestion("Barnala"),new TownSuggestion("Kapurthala"),new TownSuggestion("Tarn Taran Sahib"),new TownSuggestion("Shahid Bhagat Singh Nagar"),new TownSuggestion("Jalandhar"),new TownSuggestion("Faridkot"),new TownSuggestion("Nagaur"),new TownSuggestion("Dungapur"),new TownSuggestion("Bharatpur"),new TownSuggestion("Dausa"),new TownSuggestion("Chittorgarh"),new TownSuggestion("Bikaner"),new TownSuggestion("Ajmer"),new TownSuggestion("Jodhpur"),new TownSuggestion("Jhunjhunu"),new TownSuggestion("Karauli"),new TownSuggestion("Sirohi"),new TownSuggestion("Kota"),new TownSuggestion("Dholpur"),new TownSuggestion("Churu"),new TownSuggestion("Bundi"),new TownSuggestion("Ganganagar"),new TownSuggestion("Udaipur"),new TownSuggestion("Tonk"),new TownSuggestion("Sawai Madhopur"),new TownSuggestion("Bhilwara"),new TownSuggestion("Baran"),new TownSuggestion("Banswara"),new TownSuggestion("Jaisalmer"),new TownSuggestion("Jaipur"),new TownSuggestion("Sikar"),new TownSuggestion("Pali"),new TownSuggestion("Barmer"),new TownSuggestion("Alwar"),new TownSuggestion("Jalore"),new TownSuggestion("Hanumangarh"),new TownSuggestion("Namchi"),new TownSuggestion("Geyzing"),new TownSuggestion("Gangtok"),new TownSuggestion("Erode"),new TownSuggestion("Coimbatore"),new TownSuggestion("Vellore"),new TownSuggestion("Tiruvarur"),new TownSuggestion("Salem"),new TownSuggestion("Perambalur"),new TownSuggestion("Nagapattinam"),new TownSuggestion("Dindigul"),new TownSuggestion("Virudhunagar"),new TownSuggestion("Pudukkottai"),new TownSuggestion("Namakkal"),new TownSuggestion("Thiruvallur"),new TownSuggestion("Madurai"),new TownSuggestion("Tirunelveli"),new TownSuggestion("Viluppuram"),new TownSuggestion("Udagamandalam"),new TownSuggestion("Thoothukudi"),new TownSuggestion("Theni"),new TownSuggestion("Sivaganga"),new TownSuggestion("Nagercoil"),new TownSuggestion("Karur"),new TownSuggestion("Tiruvannamalai"),new TownSuggestion("Tiruppur"),new TownSuggestion("Thanjavur"),new TownSuggestion("Dharmapuri"),new TownSuggestion("Cuddalore"),new TownSuggestion("Ariyalur"),new TownSuggestion("Kanchipuram"),new TownSuggestion("Tiruchirappalli"),new TownSuggestion("Ramanathapuram"),new TownSuggestion("Hyderabad"),new TownSuggestion("Warangal"),new TownSuggestion("Mahabubnagar"),new TownSuggestion("Khammam"),new TownSuggestion("Sangareddi"),new TownSuggestion("Adilabad"),new TownSuggestion("Karimnagar"),new TownSuggestion("Nizamabad"),new TownSuggestion("Nalgonda"),new TownSuggestion("Agartala"),new TownSuggestion("Noida"),new TownSuggestion("Muzaffarnagar"),new TownSuggestion("Moradabad"),new TownSuggestion("Meerut"),new TownSuggestion("Mainpuri"),new TownSuggestion("Barabanki"),new TownSuggestion("Agra"),new TownSuggestion("Kanpur"),new TownSuggestion("Gorakhpur"),new TownSuggestion("Ghaziabad"),new TownSuggestion("Mirzapur"),new TownSuggestion("Deoria"),new TownSuggestion("Budaun"),new TownSuggestion("Jhansi"),new TownSuggestion("Hardoi"),new TownSuggestion("Gauriganj"),new TownSuggestion("Sultanpur"),new TownSuggestion("Lucknow"),new TownSuggestion("Bareilly"),new TownSuggestion("Banda"),new TownSuggestion("Balrampur"),new TownSuggestion("Jyotiba_Phule_Nagar"),new TownSuggestion("Jaunpur"),new TownSuggestion("Ghazipur"),new TownSuggestion("Varanasi"),new TownSuggestion("Saharanpur"),new TownSuggestion("Robertsganj"),new TownSuggestion("Rampur"),new TownSuggestion("Mathura"),new TownSuggestion("Bahraich"),new TownSuggestion("Bagpat"),new TownSuggestion("Raebareli"),new TownSuggestion("Pilibhit"),new TownSuggestion("Mahamaya-Nagar"),new TownSuggestion("Etawah"),new TownSuggestion("Etah"),new TownSuggestion("Bulandshahr"),new TownSuggestion("Bijnor"),new TownSuggestion("Auraiya"),new TownSuggestion("Aligarh"),new TownSuggestion("Fatehpur"),new TownSuggestion("Sant_Kabir_Nagar"),new TownSuggestion("Pratapgarh"),new TownSuggestion("Kushinagar"),new TownSuggestion("Chandauli"),new TownSuggestion("Ballia"),new TownSuggestion("Kaushambi"),new TownSuggestion("Gonda"),new TownSuggestion("Shahjahanpur"),new TownSuggestion("Basti"),new TownSuggestion("Firozabad"),new TownSuggestion("Faizabad"),new TownSuggestion("Sitapur"),new TownSuggestion("Orai"),new TownSuggestion("Mau"),new TownSuggestion("Mahoba"),new TownSuggestion("Maharajganj"),new TownSuggestion("Lalitpur"),new TownSuggestion("Lakhimpur-Kheri"),new TownSuggestion("Allahabad"),new TownSuggestion("Akbarpur"),new TownSuggestion("Kasganj"),new TownSuggestion("Kannauj"),new TownSuggestion("Unnao"),new TownSuggestion("Pithoragarh"),new TownSuggestion("Almora"),new TownSuggestion("Dehradun"),new TownSuggestion("Rudrapur"),new TownSuggestion("New Tehri"),new TownSuggestion("Nainital"),new TownSuggestion("Haridwar"),new TownSuggestion("Midnapore"),new TownSuggestion("Bankura"),new TownSuggestion("Alipore"),new TownSuggestion("Raiganj"),new TownSuggestion("Tamluk"),new TownSuggestion("English Bazar (Malda)"),new TownSuggestion("Baharampur"),new TownSuggestion("Cooch Behar"),new TownSuggestion("Barasat"),new TownSuggestion("Purulia"),new TownSuggestion("Bardhaman"),new TownSuggestion("Suri"),new TownSuggestion("Howrah"),new TownSuggestion("Krishnanagar"),new TownSuggestion("Hoogly")));

    public interface OnFindColorsListener {
        void onResults(List<TownWrapper> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<TownSuggestion> results);
    }

    public static List<TownSuggestion> getHistory(Context context, int count) {

        List<TownSuggestion> suggestionList = new ArrayList<>();
        TownSuggestion townSuggestion;
        for (int i = 0; i < mTownSuggestions.size(); i++) {
            townSuggestion = mTownSuggestions.get(i);
            townSuggestion.setIsHistory(true);
            suggestionList.add(townSuggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (TownSuggestion townSuggestion : mTownSuggestions) {
            townSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TownDataHelper.resetSuggestionsHistory();
                List<TownSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (TownSuggestion suggestion : mTownSuggestions) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<TownSuggestion>() {
                    @Override
                    public int compare(TownSuggestion lhs, TownSuggestion rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<TownSuggestion>) results.values);
                }
            }
        }.filter(query);

    }


    public static void findTown(Context context, String query, final OnFindColorsListener listener) {
        initColorWrapperList(context);

        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<TownWrapper> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (TownWrapper town : mTownWrappers) {
                        if (town.getTownName()
                                .startsWith(constraint.toString())) {

                            suggestionList.add(town);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<TownWrapper>) results.values);
                }
            }
        }.filter(query);

    }

    private static void initColorWrapperList(Context context) {

        if (mTownWrappers.isEmpty()) {
            String jsonString = loadJson(context, TOWN_FILE_NAME);
            mTownWrappers = deserializeTowns(jsonString);
        }
    }

    private static String loadJson(Context context, String fileName) {

        String jsonString;

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonString;
    }

    private static List<TownWrapper> deserializeTowns(String jsonString) {

        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<TownWrapper>>() {
        }.getType();
        return gson.fromJson(jsonString, collectionType);
    }
}
