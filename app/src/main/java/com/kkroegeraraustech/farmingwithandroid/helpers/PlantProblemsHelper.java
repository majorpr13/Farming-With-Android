package com.kkroegeraraustech.farmingwithandroid.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class PlantProblemsHelper {
    private String CropType = FieldHelper.Interface_CropType.CROP_DEFAULT;

    public interface Interface_CornPests {
        String NEMATOADS = "Nematoads";
        String APHIDS = "Aphids";
        String WORM_CUT = "Cutworms";
        String WORM_EAR = "Earworms";
        String WORM_ROOT = "Rootworm";
        String SPIDER_MITES = "Spidermites";
        String BORER = "Borer";
        String BEETLES_FLEA = "Flea Beetles";
        String GENERAL = "General";
    }
    public interface Interface_BeanPests {
        String NEMATOADS = "Nematoads";
        String SPIDER_MITES = "Spidermites";
        String BORER = "Borer";
        String BEETLES_BEANLEAF = "Bean Leaf Beetles";
        String GENERAL = "General";
    }

    ArrayList<String> mPestDescription = null;

    public void addPest(String additionalPest) {

        switch (CropType){
            case FieldHelper.Interface_CropType.CROP_CORN:
                for (Field F : Interface_CornPests.class.getFields()) {
                    String tmpString = F.getName();
                    if (tmpString == additionalPest) {
                        //add it and break
                        break;
                    }
                }
                break;
            case FieldHelper.Interface_CropType.CROP_SOYBEANS:
                break;
        }
    }

}
