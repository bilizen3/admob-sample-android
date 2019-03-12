package com.flores.admob_sample_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RewardedVideoAdListener {

    private lateinit var rewardedVideoAd: RewardedVideoAd
    private lateinit var adRequest: AdRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, getString(R.string.admob_app_id))

        adRequest = AdRequest.Builder()
            .addTestDevice(getString(R.string.test_device))
            .build()

        val interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.intersticial_id)
        interstitialAd.loadAd(adRequest)

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        rewardedVideoAd.rewardedVideoAdListener = this
        rewardedVideoAd.loadAd(getString(R.string.bonificado_id), adRequest)

        btnBanner.setOnClickListener {
            avBanner.loadAd(adRequest)
        }

        btnIntersticial.setOnClickListener {
            if (interstitialAd.isLoaded) interstitialAd.show()
        }

        btnBonificado.setOnClickListener {
            if (rewardedVideoAd.isLoaded) rewardedVideoAd.show()
        }
    }

    override fun onPause() {
        super.onPause()
        rewardedVideoAd.pause(this)
    }

    override fun onResume() {
        super.onResume()
        rewardedVideoAd.resume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        rewardedVideoAd.destroy(this)
    }

    override fun onRewardedVideoAdClosed() {
        rewardedVideoAd.loadAd(getString(R.string.bonificado_id), adRequest)
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdLoaded() {
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoCompleted() {
        Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show()
    }

    override fun onRewarded(p0: RewardItem?) {
        Toast.makeText(this, p0!!.type + " " + p0!!.amount, Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
    }
}
