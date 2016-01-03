package com.example.NameCard.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.NameCard.R;
import com.example.NameCard.entity.Card;
import com.example.NameCard.programs.TextureShaderProgram;
import com.example.NameCard.util.CardTextHelper;
import com.example.NameCard.util.TextureHelper;

/**
 * Created by ZTR on 1/3/16.
 */
public class CardSurface {

    private PortraitImgSection portraitImgSection;
    private MainInfoSection mainInfoSection;
    private AttachedInfoSection attachedInfoSection;

    private TextureShaderProgram portraitProgram;
    private TextureShaderProgram mainInfoProgram;
    private TextureShaderProgram attachedInfoProgram;

    private int portraitTextureId;
    private int mainInfoTextureId;
    private int attachedInfoTextureId;

    private CardTextHelper cardTextHelper;

    private Card card;

    private Context context;

    public CardSurface(Context context, Card card){
        this.card = card;
        this.context = context;
        portraitImgSection = new PortraitImgSection();
        mainInfoSection = new MainInfoSection();
        attachedInfoSection = new AttachedInfoSection();
    }

    public void initTexturePrograms(){
        portraitProgram = new TextureShaderProgram(context);
        mainInfoProgram = new TextureShaderProgram(context);
        attachedInfoProgram = new TextureShaderProgram(context);

        cardTextHelper = new CardTextHelper(card, context);
        Bitmap portraitBitmap = createBitMapFromResource(R.drawable.portrait);
        Bitmap mainInfoBitmap = cardTextHelper.createMainInfoBitmap();
        Bitmap attachedInfoBitmap = cardTextHelper.createAttachedInfoBitmap();

        portraitTextureId = TextureHelper.loadTextureFromBitMap(portraitBitmap);
        mainInfoTextureId = TextureHelper.loadTextureFromBitMap(mainInfoBitmap);
        attachedInfoTextureId = TextureHelper.loadTextureFromBitMap(attachedInfoBitmap);
    }

    public void draw(float[] matrix){
        portraitProgram.useProgram();
        portraitProgram.setUniforms(matrix, portraitTextureId);
        portraitImgSection.bindData(portraitProgram);
        portraitImgSection.draw();

        mainInfoProgram.useProgram();
        mainInfoProgram.setUniforms(matrix, mainInfoTextureId);
        mainInfoSection.bindData(mainInfoProgram);
        mainInfoSection.draw();

        attachedInfoProgram.useProgram();
        attachedInfoProgram.setUniforms(matrix, attachedInfoTextureId);
        attachedInfoSection.bindData(attachedInfoProgram);
        attachedInfoSection.draw();
    }

    private Bitmap createBitMapFromResource(int resourceId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // Read in the resource
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        return bitmap;
    }

}
