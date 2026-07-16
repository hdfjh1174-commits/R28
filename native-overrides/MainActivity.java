package com.converter.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.getcapacitor.BridgeActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BridgeActivity {

    private static final int RUNTIME_PERMISSION_REQUEST_CODE = 51427;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> toRequest = new ArrayList<>();
        String[] candidates = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO
        };
        for (String perm : candidates) {
            if (isPermissionDeclared(perm)
                    && ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                toRequest.add(perm);
            }
        }
        if (!toRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, toRequest.toArray(new String[0]), RUNTIME_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean isPermissionDeclared(String permission) {
        try {
            String[] declared = getPackageManager()
                    .getPackageInfo(getPackageName(), android.content.pm.PackageManager.GET_PERMISSIONS)
                    .requestedPermissions;
            if (declared == null) return false;
            for (String p : declared) {
                if (p.equals(permission)) return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
