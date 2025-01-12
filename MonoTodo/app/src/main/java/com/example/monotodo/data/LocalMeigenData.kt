package com.example.monotodo.data

import android.content.Context
import com.example.monotodo.network.Meigen
import com.example.monotodo.R

// フォールバック用にローカル名言リストをまとめたobject
object LocalMeigenData {
    fun getFallbackMeigens(context: Context): List<Meigen> {
        val resources = context.resources
        return listOf(
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_1_text),
                auther = resources.getString(R.string.fallback_meigen_1_auther)
            ),
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_2_text),
                auther = resources.getString(R.string.fallback_meigen_2_auther)
            ),
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_3_text),
                auther = resources.getString(R.string.fallback_meigen_3_auther)
            ),
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_4_text),
                auther = resources.getString(R.string.fallback_meigen_4_auther)
            ),
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_5_text),
                auther = resources.getString(R.string.fallback_meigen_5_auther)
            ),
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_6_text),
                auther = resources.getString(R.string.fallback_meigen_6_auther)
            ),
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_7_text),
                auther = resources.getString(R.string.fallback_meigen_7_auther)
            ),
            Meigen(
                meigen = resources.getString(R.string.fallback_meigen_8_text),
                auther = resources.getString(R.string.fallback_meigen_8_auther)
            )
        )
    }
}