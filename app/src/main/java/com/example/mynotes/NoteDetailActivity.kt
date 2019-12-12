package com.example.mynotes

    import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.db.entity.Tag
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup


@Suppress("DEPRECATION")
class NoteDetailActivity : AppCompatActivity() {

    lateinit var titleView: EditText
    lateinit var contentView: EditText
    lateinit var chipGroup: ChipGroup
    lateinit var tagAdder: AutoCompleteTextView
    private lateinit var detailViewModel: NoteDetailViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        titleView = findViewById(R.id.title)
        contentView = findViewById(R.id.content)
        chipGroup = findViewById(R.id.tag_chips)
        tagAdder = findViewById(R.id.tag_adder)

        val intent = this.intent
        val id: Long = intent.getLongExtra("noteId", 0)
        val viewModelFactory = DetailViewModelFactory(id, application)
        detailViewModel = ViewModelProvider(this, viewModelFactory).get(NoteDetailViewModel::class.java)

        if(intent.action == Intent.ACTION_EDIT)
        {
            titleView.setText(detailViewModel.note.title)
            contentView.setText(detailViewModel.note.content)
            setTags(detailViewModel.tags)
        }
        setTagAdder()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.note_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.delete) {
            detailViewModel.deleteNote()
            finish()
            return true
        }
        if (id == R.id.done) {
            onFinishEditing()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onFinishEditing(){
        if (contentView.text.isEmpty())
            Toast.makeText(this, "Content must not be empty.", Toast.LENGTH_LONG).show()
        else {
            detailViewModel.note.title = titleView.text.toString()
            detailViewModel.note.content = contentView.text.toString()
            detailViewModel.saveNote()
        }
    }

    private fun setTags(tagList: List<Tag>) {
        for (tag in tagList) {
            putChip(tag.name)
        }
    }

    private fun setTagAdder(){
        val adapter = ArrayAdapter<Tag>(
            this, // Context
            android.R.layout.simple_dropdown_item_1line,
            detailViewModel.allTags
        )

        tagAdder.apply {
            setAdapter(adapter)
            threshold = 1
            onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val tag: Tag = parent.adapter.getItem(position) as Tag
                    if (detailViewModel.addTag(tag))
                        putChip(tag.name)
                    tagAdder.text.clear()
                }

            onFocusChangeListener = View.OnFocusChangeListener { _, b ->
                if (b) {
                    tagAdder.showDropDown()
                }
            }

            setOnClickListener {
                tagAdder.showDropDown()
            }

        }
    }

    private fun putChip(name: String){
        val chip = Chip(this, null, R.attr.CustomChipChoiceStyle)
        val paddingDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 10f,
            resources.displayMetrics
        ).toInt()
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)

        chip.text = name

        chip.setCloseIconResource(R.drawable.ic_remove)
        chip.isCloseIconEnabled = true
        chipGroup.addView(chip as View)

        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip as View)
            detailViewModel.removeTag(chip.text.toString())
        }
    }

}
