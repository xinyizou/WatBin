package com.example.watbin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_search.*

class SearchFragment : Fragment() {

    var itemList = generateItemData()
    var adapter = ItemAdapter(itemList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.search, container, false)

        val search_field = view.findViewById<EditText>(R.id.search_field)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)

        (recycler.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations = false

        recycler.setLayoutManager(LinearLayoutManager(context))
        recycler.setAdapter(adapter)
        recycler.setHasFixedSize(true)

        search_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
        } )

        search_field.setOnEditorActionListener(DoneEditorActionListener())
//        container.setOnClickListener(LostFocusActionListener())
//        recycler.setOnClickListener(LostFocusActionListener())
        setupUI(view.findViewById(R.id.recycler))
        if (view.findViewById<TextView>(R.id.item_name) != null) {
            setupUI(view.findViewById(R.id.item_layout))
        }

        return view
    }

    fun setupUI(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.getWindowToken(),0)
                    v?.clearFocus()

                    return false
                }
            })
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until (view).childCount) {
                val innerView = (view).getChildAt(i)
                setupUI(innerView)
            }
        }
    }


    private fun filter(text: String) {
        val filteredList = ArrayList<Item>()

        for (item in itemList) {
            if (item.getItemName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }

        adapter.filterList(filteredList)
    }

    public fun generateItemData(): ArrayList<Item> {
        var list = ArrayList<Item>()
        var item = Item()

        item.name = "BATTERIES"
        item.description = "Batteries are considered hazardous materials and should not be put in the garbage or any recycling bins. Drop them off at the following battery bins around campus:\n" +
                "\n" +
                "SLC: Lower Atrium across from Campus Tech Shop\n" +
                "DP: Main floor, across from Circulation Desk\n" +
                "DC: Main entrance\n" +
                "V1: Front desk\n" +
                "UWP: Beck Hall, front desk \n" +
                "CLV: Community Centre, front desk\n" +
                "If you have collected a number of batteries in your department, you may also call Greg Friday (ext. 35755) from the Safety Office to pick them up, or drop them off at any hazardous materials office."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "BOOKS"
        item.description = "On campus: Softcover books or hardcover books with the cover removed can be placed in Papers (grey) recycling bins. You can also donate used textbooks and books to the Textbooks for Change program; please refer to the Textbooks drop down tab for a list of drop-off locations. \n" +
                "\n" +
                "At home: All books can be recycled in the Paper and Bags bin through the Region of Waterloo curbside recycling program. "
        item.category = Category.PAPERS
        list.add(item)

        item = Item()
        item.name = "BOTTLES, GLASS"
        item.description = "Glass bottles can be placed in the Containers (blue) recycling bins."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "BOTTLES, PLASTIC"
        item.description = "Plastic bottles can be placed in the Containers (blue) recycling bins."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "BOXBOARD"
        item.description = "On campus: Boxboard (eg. tissue boxes, cereal boxes) are considered cardboard on campus and should be folded and placed next to recycling depots/stations or brought to outdoor bins. \n" +
                "\n" +
                "At home: The Region of Waterloo collects boxboard in the Papers and Bags bin through their curbside recycling collection. "
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "CARDBOARD"
        item.description = "Please fold cardboard boxes and place next to a recycling depot."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "CANS"
        item.description =  "Cans can be placed in the Containers (blue) recycling bins."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "CELL PHONES"
        item.description = "Cell phones can be dropped off in several e-waste bins across campus:\n" +
                "\n" +
                "DC: Library\n" +
                "SLC: Lower Atrium\n" +
                "V1: Front desk"
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "CHIP BAGS"
        item.description =  "Chip bags are not recyclable and should be placed in the Garbage (black) bins."
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "CLING WRAP"
        item.description =  "Cling wrap is not recyclable and should be placed in the Garbage (black) bins."
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "CLOTHING"
        item.description =  "If you have used clothing, bring it to one of these drop off locations managed by Sustainable Campus Initiative! They sell or recycle all used textiles.\n" +
                "\n" +
                "CLV: Laundry room\n" +
                "V1: In front of laundry room \n" +
                "UWP: Grand Commons Community Centre, near Tim Horton's\n" +
                "CMH: Beside front desk by window\n" +
                "MKV: Opposite to front desk\n" +
                "REV: Opposite to front desk\n" +
                "Velocity Residence: By front desk"
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "COFFEE CUPS"
        item.description =  "Coffee cups are treated differently on and off campus. \n" +
                "\n" +
                "On campus: please separate the cup and lid. Place the cup in the Organics (green) bins and the lid in the Containers (blue) bins. If no Organics bin is available, please place the cup in the Papers (grey) bins and the lid in the Containers (blue) bins.\n" +
                "\n" +
                "At home: Coffee cups and lids are both placed in your containers bin in the Region of Waterloo. \n" +
                "\n" +
                "Did you know that last year, coffee cups made up nearly 8% of our garbage on campus?\n" +
                "\n" +
                "Remember, reducing is always better than recycling. Whenever possible, bring your own mug and receive a 20 cent discount on coffee/tea at all Food Service locations, and a 10 cent discount at all franchise locations on campus."
        item.category = Category.ORGANIC
        list.add(item)

        item = Item()
        item.name = "COFFEE TRAYS"
        item.description =  "On campus: Coffee trays are considered cardboard on campus and should be placed next to a recycling depot. \n" +
                "\n" +
                "At home: The Region of Waterloo collects coffee trays in the Papers and Bags bin through their curbside recycling collection. "
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "COMPOSTABLE PLASTIC CUPS"
        item.description =  "Plastic cups labelled as \"compostable\" should be placed in the Containers (blue) recycling bins."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "COMPUTERS AND COMPUTER MONITORS"
        item.description = "Computers, monitors and other computer equipment can be dropped off at Central Stores, East Campus Hall. \n" +
                "\n" +
                "Please fill out an asset disposal form and follow any other instructions available on the Central Stores website."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "CONTAINERS, GLASS"
        item.description = "Glass containers can be placed in the Containers (blue) recycling bins."
        item.category = Category.CONTAINERS
        list.add(item)

        item.name = "CONTAINERS, PLASTIC"
        item.description = "Plastic containers with label 1-7 can be placed in the Containers (blue) recycling bins.\n" +
                "\n" +
                "Please dump any food waste into the Organics (green) bin (if not available, place food waste in the Garbage (black) bin)."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "CUTLERY, COMPOSTABLE"
        item.description = "Compostable cutlery, including wooden chopsticks, wooden skewers, and wooden stir sticks, can be placed in the Organics (green) bin. "
        item.category = Category.ORGANIC
        list.add(item)

        item = Item()
        item.name = "CUTLERY, PLASTIC"
        item.description = "On campus: Plastic cutlery with label 1-7 can be placed in the Containers (blue) bin on campus.\n" +
                "\n" +
                "At home: The Region of Waterloo does not recycle plastic cutlery, and it should be placed in the garbage."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "E-WASTE"
        item.description = "Small e-waste items (e.g. keyboards, mice, writing utensils, ink/toner cartridges, cell phones, cables, cameras, tablets) can be dropped off in several e-waste bins across campus:\n" +
                "\n" +
                "DC: Library\n" +
                "SLC: Lower Atrium\n" +
                "V1: Front desk\n" +
                "Larger e-waste items can be dropped off at Central Stores, East Campus Hall. For a full list of acceptable items and drop-off procedures, please visit the Central Stores website."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "EGG CARTONS"
        item.description = "On campus: Egg cartons are considered cardboard on campus and should be placed next to a recycling depot. \n" +
                "\n" +
                "At home: The Region of Waterloo collects egg cartons in the Papers and Bags bin through their curbside recycling collection. "
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "ENVELOPES"
        item.description = "Envelopes with or without windows can be placed in the Paper (grey) recycling bins."
        item.category = Category.PAPERS
        list.add(item)

        item = Item()
        item.name = "FOOD WASTE"
        item.description =  "Food waste can be placed in the Organics (green) bins, which will be available in main Food Services locations across campus. Please do not place any plastics in the organics bins! \n" +
                "\n" +
                "Food waste includes:\n" +
                "\n" +
                "Food scraps\n" +
                "Coffee grounds \n" +
                "Coffee filters\n" +
                "Tea bags \n" +
                "Bones/shells \n" +
                "Please make sure you collect your organics in a certified compostable bag. \n" +
                "\n" +
                "Campus Compost also has several bins where you can drop off food waste for collection in our on-site composter. For all locations, visit the Campus Compost website.\n" +
                "\n" +
                "If you are interested in organics collection in your department (e.g. kitchen, lounge), please contact Campus Compost to inquire about their pick-up service."
        item.category = Category.ORGANIC
        list.add(item)

        item = Item()
        item.name = "FOUNTAIN DRINK CUPS"
        item.description = "Fountain drink cups (wax lined) are not recyclable and must be placed in the Garbage (black) bins. "
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "FURNITURE"
        item.description = "Used furniture from office renovations can be recycled through Central Stores, East Campus Hall.\n" +
                "\n" +
                "Please fill out an asset disposal form and follow any other instructions available on the Central Stores website."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "HAZARDOUS WASTE"
        item.description = "Hazardous waste is toxic and should not be placed in the garbage or any recycling bins. For more information on proper handling of hazardous waste materials, visit the Safety Office's Hazardous Waste Standard.\n" +
                "\n" +
                "If you have a large amount of hazardous waste that needs to be collected, please contact Greg Friday (ext. 35755) from the Safety Office."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "INK AND TONER CARTRIDGES"
        item.description = "Ink and toner cartridges can be dropped off in several e-waste bins across campus:\n" +
                "\n" +
                "DC: Library\n" +
                "SLC: Lower Atrium\n" +
                "V1: Front desk\n" +
                "They can also be dropped off at Central Stores, East Campus Hall."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "JUICE BOXES"
        item.description = "Juice boxes can be placed in the Containers (blue) recycling bins. Please remove any plastic straws and place them in the garbage."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "KEYBOARDS AND MICE"
        item.description = "Keyboards and mice can be dropped off in several e-waste bins across campus:\n" +
                "\n" +
                "DC: Library\n" +
                "SLC: Lower Atrium\n" +
                "V1: Front desk"
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "LIGHTBULBS"
        item.description = "Lightbulbs are considered a hazardous material and should not be thrown into the garbage or recycling bins. Please contact Greg Friday (ext. 35755) from the Safety Office if you have a surplus of lightbulbs that need to be disposed of."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "MAGAZINES"
        item.description = "Magazines can be placed in the Papers (grey) recycling bins."
        item.category = Category.PAPERS
        list.add(item)

        item = Item()
        item.name = "MILK/CREAM CARTONS"
        item.description = "Milk and cream cartons can be placed in the Containers (blue) recycling bins."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "NAPKINS, PAPER"
        item.description = "Paper napkins can be placed in the Organics (green) bins, which will be available in main Food Services locations across campus. Please make sure that you do not place any plastics in the organics bins!"
        item.category = Category.ORGANIC
        list.add(item)

        item = Item()
        item.name = "NEWSPAPER"
        item.description = "Newspaper can be placed in the Papers (grey) recycling bins."
        item.category = Category.PAPERS
        list.add(item)

        item = Item()
        item.name = "PAPER"
        item.description = "Office paper can be placed in the Papers (grey) recycling bins.\n" +
                "\n" +
                "You can also drop off coloured and white printer/copy paper in Fine Paper (white) bins, located near printer queues and in copy rooms. \n" +
                "\n" +
                "Fun Fact: All fine paper collected through the white bin program is sent to a recycling facility that re-uses the materials as paper towels. These get shipped back to campus for use in the washrooms!"
        item.category = Category.PAPERS
        list.add(item)

        item = Item()
        item.name = "PAPER PLATES"
        item.description = "Paper plates can be placed in the Organics (green) bins, which are available in main Food Services locations across campus. Please make sure that you do not place any plastics in the organics bins!"
        item.category = Category.ORGANIC
        list.add(item)

        item = Item()
        item.name = "PAPER TOWELS"
        item.description = "Paper towels can be placed in the Organics (green) bins. Custodial staff will collect paper towels from washrooms and put them in the Organics bins, and you can also find Organics bins at main Food Services locations. "
        item.category = Category.ORGANIC
        list.add(item)

        item = Item()
        item.name = "PENS/PENCILS"
        item.description = "Pens, pencils and other writing utensils can be dropped off in several e-waste bins across campus:\n" +
                "\n" +
                "DC: Library\n" +
                "SLC: Lower Atrium\n" +
                "V1: Front desk"
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "PIZZA BOXES"
        item.description = "Pizza boxes (empty!) can be broken down and placed beside recycling depots."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "PLASTIC BAGS"
        item.description = "On campus: Plastic bags (eg. shopping bags, bread bags, ziploc bags) cannot be recycled on campus and should be placed in the Garbage (black) bins.\n" +
                "\n" +
                "At home: Plastic bags can be recycled through Region of Waterloo curbside collection and can be placed in your Paper and Bags recycling bins. If you have multiple bags, please place them into one bag and place them in the bin."
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "PLASTIC STRAPPING"
        item.description = "Plastic strapping from shipping boxes or other products should be placed in the Garbage (black) bins."
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "PLASTIC WRAPPING"
        item.description = "On campus: Soft plastic wrapping (eg. water bottle or pop case wrapping) cannot be recycled on campus and should be placed in the Garbage (black) bins.\n" +
                "\n" +
                "At home: The Region of Waterloo collects soft plastic wrapping with other plastic bags in the Paper and Bags bin through curbside recycling. "
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "POST-IT NOTES"
        item.description = "Post-it notes are recyclable and can be placed in the Papers (grey) bins. "
        item.category = Category.PAPERS
        list.add(item)

        item = Item()
        item.name = "RAZORS"
        item.description = "Disposable razors and razor blades are not recycable and should be placed in the Garbage (black) bins. "
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "RECEIPTS"
        item.description = "Receipts are recyclable and can be placed in the Papers (grey) bins."
        item.category = Category.PAPERS
        list.add(item)

        item = Item()
        item.name = "SCRAP METAL"
        item.description = "Scrap metal can be recycled through Central Stores, East Campus Hall.\n" +
                "\n" +
                "Please fill out an asset disposal form and follow any other instructions available on the Central Stores website."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "SCRAP WOOD AND BRUSH"
        item.description = "Scrap wood and brush can be collected by Plant Operations (ext. 33793) and converted into mulch. "
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "STRAWS, PAPER"
        item.description = "Paper straws can be placed in the Organics (green) bins, which are available in main Food Services locations across campus. If there are no green bins available, please place used paper straws in the garbage."
        item.category = Category.ORGANIC
        list.add(item)

        item = Item()
        item.name = "STRAWS, PLASTIC"
        item.description = "Plastic straws are not recyclable and must be placed in the Garbage (black) bins.\n" +
                "\n" +
                "Fun Fact: The University of Waterloo is phasing out plastic straws across campus by Fall 2018 and will replace them with paper straws. When eating out off-campus, consider purchasing a stainless steel straw - they are portable, easy to clean, and help keep plastic out of our oceans and natural environment!"
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "SHIPPING BOXES"
        item.description = "Shipping boxes can be broken down and placed beside recycling depots."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "STRYOFOAM"
        item.description = "Styrofoam is not recyclable and must be placed in the Garbage (black) bins."
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "TELEVISIONS, SMALL"
        item.description = "Small televisions can be dropped off at Central Stores, East Campus Hall. \n" +
                "\n" +
                "Please fill out an asset disposal form and follow any other instructions available on the Central Stores website."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "TEXTBOOKS"
        item.description = "Textbooks can be recycled at the following locations:\n" +
                "\n" +
                "Feds Used Books (SLC lower atrium)\n" +
                "ML 355\n" +
                "EV1 138A\n" +
                "\u200BPAS 2012\n" +
                "Library - Davis \n" +
                "Library - Porter\n" +
                "Library - St. Jerome's\n" +
                "Balsille School - 67 Erb St. W.\n" +
                "St. Paul's - Next to the Greenhouse Office\n" +
                "School of Pharmacy - Victoria Street (Need a FOB to access)\n" +
                "V1 Community Center - Hallway to the right of the front desk\n" +
                "UWP Grand Commons Community centre - Between Eby and Beck Hall \n" +
                " View the Campus Map for the above locations."
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "TIN FOIL"
        item.description = "Clean tin foil and aluminum foil trays can be placed in the Containers (blue) recycling bins.\n" +
                "\n" +
                "If your tin foil is dirty (e.g. baked on food), then it must be placed in the Garbage (black) bins."
        item.category = Category.CONTAINERS
        list.add(item)

        item = Item()
        item.name = "TOYS"
        item.description = "Toys are not recyclable but is suitable for donation. Visit the Region of Waterloo's Residential Reduce and Reuse Options page > Directory of local programs, businesses and organizations > Toys for a list of donation locations. \n" +
                "\n" +
                "Otherwise, toys should be placed into the Garbage (black) bins. "
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "WAX PAPER"
        item.description = "Wax paper (eg. Subway wraps, Tim Horton's bagel/sandwich liners) is not recyclable and must be placed in the Garbage (black) bins."
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "WRAPPERS, PLASTIC"
        item.description = "Plastic wrappers (eg. chocolate bars, granola bars, candy) are not recyclable and must be placed in the Garbage (black) bins."
        item.category = Category.GARBAGE
        list.add(item)

        item = Item()
        item.name = "WRITING UTENSILS"
        item.description = "TWriting utensils can be dropped off in several e-waste bins across campus:\n" +
                "\n" +
                "DC: Library\n" +
                "SLC: Lower Atrium\n" +
                "V1: Front desk"
        item.category = Category.OTHER
        list.add(item)

        item = Item()
        item.name = "ZIPLOCK BAGS"
        item.description = "On campus: Ziploc bags cannot be recycled on campus and should be placed in the Garbage (black) bins. \n" +
                "\n" +
                "At home: Clean ziploc bags can be recycled through Region of Waterloo curbside collection and can be placed in your Paper and Bags recycling bin. If you have multiple bags, please place them in one bag and place that bag in your recycling bin. "
        item.category = Category.GARBAGE
        list.add(item)

        return list
    }

}