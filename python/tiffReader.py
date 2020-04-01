import json
from PIL import Image
from PIL.TiffTags import TAGS
Image.MAX_IMAGE_PIXELS = None


pli_path = r"TIFF_FILE_PATH"
with Image.open(pli_path) as img:
    meta_dict = {TAGS[key] : img.tag[key] for key in img.tag.keys()}
    print(meta_dict)

    with open('matadata.json', 'w') as fp:
        json.dump(meta_dict, fp, indent=4)
