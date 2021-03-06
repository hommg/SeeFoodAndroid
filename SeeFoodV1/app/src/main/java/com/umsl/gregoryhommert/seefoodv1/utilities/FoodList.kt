package com.umsl.gregoryhommert.seefoodv1.utilities

class FoodList {
    //MARK:- Vars
    var items: ArrayList<String>
        private set

    //MARK:- Inits
    init {
        this.items = arrayListOf("artichoke","arugula","asparagus","green"," purple"," white","avocado","bamboo shoots",
                "bean sprouts","beans- see bean list","beet","belgian endive","bell pepper","green pepper",
                "bitter melon/bitter gourd","bok choy/bok choi/pak choy","broccoli","brussels sprouts","burdock root",
                "gobo","cabbage","green"," red"," savoy","calabash","capers","carrot","cassava/yuca","cauliflower","celery",
                "celery root","celeriac","celtuce","chayote","chinese broccoli/kai-lan","corn/maize","baby corn/candle corn",
                "cucumber","english cucumber","gherkin","pickling cucumbers","daikon","beefsteak tomato","egg", "eggs",
                "radish","edamame","eggplant","aubergine","elephant garlic","endive","curly","frisee","escarole","fennel",
                "fiddlehead","galangal","garlic","ginger","grape leaves","green beans","string beans","snap beans","wax beans",
                "greens","amaranth leaves","chinese spinach","beet greens","collard greens","dandelion greens","kale",
                "kohlrabi greens","mustard greens","rapini","spinach","swiss chard","turnip greens","hearts of palm","horseradish",
                "jerusalem artichoke","sunchokes","jícama","kale","curly","lacinato","ornamental","kohlrabi","leeks","lemongrass",
                "lettuce","butterhead","bibb","boston","iceberg","green leaf","red leaf","romaine","lotus root","lotus seed",
                "mushrooms","napa cabbage","nopales","okra","olive","onion","green onions","scallions","parsley","parsley root",
                "parsnip","peas","green peas","snow peas","sugar snap peas","peppers","plantain","potato","pumpkin","purslane",
                "radicchio","radish","rutabaga","sea vegetables","shallots","spinach","squash","sweet potato","swiss chard","taro",
                "tomatillo","tomato","turnip","water chestnut","water spinach","watercress","winter melon","yams","zucchini","apple",
                "apricot","avocado","banana","berries","breadfruit","carob","cherry","citron","coconut","date","dragon fruit","pitaya",
                "durian","fig","ginger","grapes","currant","raisin","grapefruit","guava","jackfruit","jujube","kiwifruit","kumquat",
                "lemon","lime","longan","loquat","lucuma","lychee","mamey sapote","mango","mangosteen","melons","nance","nectarine",
                "noni","oranges","blood orange","mandarin","clementine","satsuma","navel","seville","valencia","papaya","passion fruit",
                "peach","pear","asian pear","persimmon","pineapple","plantain","plum","damson","prunes","pomegranate","pomelo",
                "prickly pear","cactus pear","quince","rambutan","rhubarb","starfruit","tamarillo","tamarind","tangerine","tangelo",
                "tomato","","adzuki beans","anasazi beans","black beans","black turtle beans","black-eyed peas","borlotti","cranberry",
                "roman","butter beans","cannellini beans","christmas lima bean","chestnut lima","corona beans","fava beans","broad beans",
                "flageolet beans","garbanzo beans","chickpeas","desi","bengal gram","chana dal(split)","great northern beans",
                "kidney beans","lentils","lima beans","butter beans","lupini beans","marrow beans","moth beans","mung beans","navy beans",
                "pigeon pea","pink beans","pinto beans","red beans","small red beans","rice beans","scarlet runner beans","soybean",
                "soya bean","spanish tolosana beans","split peas","tepary beans","urad","","grains","amaranth","barley","hulled barley",
                "barley groats","scotch barley","pearl","pearled barley","barley flakes","barley grits","buckwheat","buckwheat groats",
                "unroasted buckwheat","kasha","roasted buckwheat","buckwheat grits","corn","hominy","popcorn","millet","oats","oat groats",
                "rolled oats","old fashioned oatmeal","steel cut oats","irish oatmeal","quick cooking oats","quick oatmeal","instant oats",
                "instant oatmeal","oat bran","quinoa","rice","rye","rye berries","cracked rye","rye flakes","sorghum","spelt",
                "spelt berries","spelt flakes","teff","triticale","triticale berries","triticale flakes","wheat","wheat berries",
                "bulgur wheat","cracked wheat","farina","semolina","pasta","couscous","wheat bran","wheat flakes","seitan",
                "wheat gluten","farro","kamut","durum wheat","wild rice","china jute","climbing wattle","paracress","common marshmallow",
                "purple amaranth","common amaranth","prickly amaranth","amaranth","slender amaranth","celery","garden orache","bank cress",
                "chik-nam, kra don","indian spinach","chard","sea beet","common borage","abyssinian cabbage","indian mustard","rutabaga",
                "rape kale","black mustard","wild cabbage","kale","kai-lan","cauliflower","cabbage","brussels sprouts","broccoli",
                "turnip","bok choi","chinese savoy","mizuna","napa cabbage","rapini","rampion","harebell","caper","wild coxcomb",
                "asian pennywort, gotukola","lamb's quarters","american wormseed","southern huauzontle","good king henry","tree spinach",
                "oak-leaved goosefoot","huauzontle","quinoa","red goosefoot","garland chrysanthemum","endive","curly endive",
                "broad-leaved endive","chicory","radicchio","cabbage thistle","miner's lettuce","siberian spring beauty",
                "chaya or tree spinach","ivy gourd","taro","jew's mallow","cilantro, coriander","sea kale","redflower ragleaf",
                "phak tiu som or phak tiu daeng","samphire","chipilín","mitsuba","caigua","cardoon","vegetable fern","arugula",
                "lesser jack","bhandhanya, culantro","fennel","scarlina","gallant soldier","ground ivy","lotus sweetjuice","melindjo",
                "okinawan spinach","sea purslane","roselle","shortpod mustard","sea sandwort","fishwort","john's cabbage","shawnee salad",
                "spotted cat's-ear","catsear","golden samphire","elecampane","water spinach","sweet potato","lablab","indian lettuce",
                "lettuce","celtuce","prickly lettuce","bottle gourd","dragon's head","white deadnettle","henbit deadnettle",
                "red deadnettle","nipplewort","ku??afila","bush banana","hawkbit","field pepperweed","dittander","maca","garden cress",
                "virginia pepperweed","decne","phak kratin","lovage","genjer","rice paddy herb, ngò om","gooseneck loosestrife",
                "cheeseweed","mallow","musk mallow","cassava","kogomi","duo rui gao he cai","japanese mint","habek mint","sea bluebell",
                "ice plant","seep monkey flower","mauka","drumstick tree","south-west african moringa","ethiopian moringa","wall lettuce",
                "ujuju","parrot feather","cicely","watercress","phak chet","fragrant water lily","water snowflake","yellow floating heart",
                "sweet basil","thai basil","lemon basil","water celery","common evening primrose","hooker's evening-primrose",
                "sensitive fern","pheka","rice","cinnamon fern","interrupted fern","common wood sorrel","creeping woodsorrel","iron cross",
                "redwood sorrel","common yellow woodsorrel","oca","mountain sorrel","money tree","petai","blue palo verde","parsnip",
                "golden lace","empress tree","burra gookeroo","clearweed","barbados gooseberry","perilla","water pepper","arctic butterbur",
                "parsley","runner bean","lima bean","bean","common reed","rough fogfruit","star gooseberry","myrobalan",
                "round-headed rampion","indian pokeberry","american pokeweed","bella sombra","deer calalu","aniseed","burnet saxifrage",
                "japanese red pine","mexican pepperleaf","west african pepper","cha-phlu","queensland grass-cloth plant","tree lettuce",
                "chinese pistache","terebinth","water lettuce","garden pea","buckshorn plantain","long-leaved plantain",
                "broad-leaved plantain","himalayan mayapple","knotweed","bistort","american bistort","alpine bistort","trifoliate orange",
                "common purslane","elephant bush","cowslip","primrose","kerguelen cabbage","prairie turnip","lungwort",
                "birch-leaved pear","lesser celandine","wild radish","radish","chinese radish","raffia palm","french scorzonera",
                "meadow beauty","roseroot","nikau","blackcurrant","seven sisters rose","sorrel","glasswort","weeping willow",
                "rosegold pussy willow","saltwort","land seaweed","opposite leaved saltwort","toothbrush tree","salad burnet",
                "great burnet","sassafras","katuk","eastern swamp saxifrage","creeping rockfoil","tagarnina","spotted golden thistle",
                "scorzonera","baikal skullcap","chayote","love-restorer","spreading stonecrop","jenny's stonecrop","rose crown",
                "livelong","digutiyara","cassod tree","sésame de gazelle","sesame","benniseed","west indian pea","sesban","sea purselane",
                "palm-grass","arrowleaf sida","pepper saxifrage","moss campion","bladder campion","blessed milk thistle","white mustard",
                "charlock","london rocket","hedge mustard","alexanders","chinese potato","field sow-thistle","spiny-leaved sow thistle",
                "sow thistle","pagoda-tree","toothache plant","spinach","greater duck-weed","otaheite apple","yellow mombin","jocote",
                "common chickweed","natal orange","sea blite","malay apple","jewels of opar","tansy","dandelion","fluted gourd",
                "new zealand spinach","portia tree","pennycress","common thyme","chinese mahogany","windmill palm","western salsify",
                "salsify","goat's beard","alsike clover","red clover","white clover","sweet trefoil","wake-robin","white trillium",
                "painted trillium","garden nasturtium","dwarf nasturtium","mashua","coltsfoot","ulluco","siberian elm","rose mallow",
                "stinging nettle","annual nettle","italian corn salad","corn salad","european verbena","bitter leaf","water speedwell",
                "brooklime","canada violet","sweet violet","bird's foot violet","common blue violet","amur grape","california wild grape",
                "northern fox grape","grape","wasabi","japanese wisteria","yellowhorn","awapuhi")
    }

}