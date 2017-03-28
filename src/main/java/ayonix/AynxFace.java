
package ayonix;

import java.awt.geom.Point2D;
import java.awt.Rectangle;

public class AynxFace
{
        private Rectangle location;
        private Rectangle mugshotLocation;
        private byte[] mugshotData;
        
        private boolean isValid;
        private int quality;
        private int occlusion;
        private float gender;
        private int age;
        public byte[] getMugshotData() {
			return mugshotData;
		}

		public void setMugshotData(byte[] mugshotData) {
			this.mugshotData = mugshotData;
		}
		private float[] rpy = new float[3];
        
        private byte[] reserved;

//        private AynxFace() {}   // prevent user creation

        public Rectangle getLocation() { return location; }
        public Rectangle getMugshotLocation() { return mugshotLocation; }
        
        public boolean isValid() { return isValid; }
        public int getQuality() { return quality; }
        public int getOcclusion() { return occlusion; }
        public float getGender() { return gender; }
        public int getAge() { return age; }
        public float[] getAngles() { return rpy; }
}

