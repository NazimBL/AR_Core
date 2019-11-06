package com.deadline_tech.ar_core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.function.Consumer;
import java.util.function.Function;

public class AR_Test extends AppCompatActivity {

    ArFragment arFragment;
    ModelRenderable modelRenderable;
    ImageView img;
    ViewRenderable viewRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar__test);

        arFragment=(ArFragment)getSupportFragmentManager().findFragmentById(R.id.mySurface_id);
        img=(ImageView)findViewById(R.id.img);

        ModelRenderable.builder().setSource(this,R.raw.roskopp).build().thenAccept(new Consumer<ModelRenderable>() {
            @Override
            public void accept(ModelRenderable renderable) {
                modelRenderable = renderable;
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {

                Toast.makeText(AR_Test.this, "Coudnt display", Toast.LENGTH_SHORT).show();
            return null;
            }
        });
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                Anchor anchor=hitResult.createAnchor();
                AnchorNode node=new AnchorNode(anchor);
                node.setParent(arFragment.getArSceneView().getScene());
                TransformableNode transformableNode=new TransformableNode(arFragment.getTransformationSystem());
                transformableNode.setParent(node);
                transformableNode.setRenderable(modelRenderable);
                transformableNode.select();
            }
        });
    }
}
