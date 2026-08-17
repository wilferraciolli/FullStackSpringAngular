import fitz
import pytesseract
from PIL import Image

OCR_DPI = 200


def parse_pdf(data: bytes) -> dict:
    """Extract text from a PDF's bytes.

    Pages with a native text layer are read directly; pages with no
    extractable text (scanned/image-based) are rasterized and run through
    Tesseract OCR instead.
    """
    document = fitz.open(stream=data, filetype="pdf")
    try:
        pages = []
        used_ocr = False

        for page in document:
            text = page.get_text().strip()
            if not text:
                pixmap = page.get_pixmap(dpi=OCR_DPI)
                image = Image.frombytes("RGB", (pixmap.width, pixmap.height), pixmap.samples)
                text = pytesseract.image_to_string(image).strip()
                used_ocr = True
            pages.append(text)

        return {
            "text": "\n\n".join(pages).strip(),
            "pageCount": len(pages),
            "usedOcr": used_ocr,
        }
    finally:
        document.close()
