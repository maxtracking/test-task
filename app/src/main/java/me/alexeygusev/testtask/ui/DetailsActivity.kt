package me.alexeygusev.testtask.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import me.alexeygusev.testtask.R
import me.alexeygusev.testtask.api.models.Item
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    private var itemData: Item? = null

//    val imageUrl = intent.extras?.getString(EXTRA_IMAGE_URL)
//    val titleText = intent.extras?.getString(EXTRA_TITLE) ?: "DETAILS"
//    val desc = intent.extras?.getString(EXTRA_DESCRIPTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        itemData = intent.extras?.getSerializable(EXTRA_DATA) as? Item

        itemData?.let {
//            val item_id = intent.extras?.getInt(EXTRA_ITEM_ID) ?: 0
//            val imageUrl = intent.extras?.getString(EXTRA_IMAGE_URL)
//            val titleText = intent.extras?.getString(EXTRA_TITLE) ?: "DETAILS"
//            val desc = intent.extras?.getString(EXTRA_DESCRIPTION)

            supportActionBar?.title = getString(R.string.details_title, it.id)
            Glide.with(this)
                .load(it.imageUrl)
                .placeholder(R.drawable.ic_block_black_24dp)
                .into(image)
            imageTitle.text = it.title
            imageDescription.text = it.description
        }

        imageDescription.movementMethod = ScrollingMovementMethod()

        sendEmail.setOnClickListener {
            sendEmail()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item?.itemId == R.id.sendEmail) {
            sendEmail()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendEmail() {
        val chooserIntent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mailto:");
            type = "image/*"
            putExtra(Intent.EXTRA_SUBJECT, "Item info")
            putExtra(Intent.EXTRA_TEXT,
                "ID: ${itemData?.id}\nTITLE: ${itemData?.title}\nIMAGE URL: ${itemData?.imageUrl}\n\n${itemData?.description}")

            try {
                val bitmap = getBitmapFromView(image, image.height, image.width)
                bitmap?.let {
                    val dataDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "/attachments")
                    dataDir.mkdirs()
                    val file = File(dataDir, "attachment_${itemData?.id}.jpg")
                    val fileCreated: Boolean = file.createNewFile()
                    if (fileCreated) {
                        val outputStream = FileOutputStream(file)
                        it.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream.close()
                    }
                    val apkURI: Uri = FileProvider.getUriForFile(
                        this@DetailsActivity,
                        applicationContext.packageName.toString() + ".provider",
                        file
                    )
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    putExtra(Intent.EXTRA_STREAM, apkURI);
                }
            } catch (ex: IOException) {
                Timber.e("SAVE FAILED: could not save file")
            }
        }

        try {
            startActivity(
                Intent.createChooser(
                    chooserIntent,
                    getString(R.string.send_email_title)
                )
            );
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.email_error), Toast.LENGTH_SHORT).show();
        }
    }

    fun getBitmapFromView(view: View, totalHeight: Int, totalWidth: Int): Bitmap? {
        val returnedBitmap =
            Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }
}
